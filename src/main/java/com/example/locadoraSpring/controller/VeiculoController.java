package com.example.locadoraSpring.controller;
import com.example.locadoraSpring.model.Veiculo;
import com.example.locadoraSpring.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping()
    public List<Veiculo> listarTodos() {
        return veiculoService.listarTodos();
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Veiculo> buscarPorPlaca(@PathVariable String placa) {
        return veiculoService.buscarPorId(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Veiculo cadastrar(@RequestBody Veiculo v) {
        return veiculoService.salvar(v);
    }

    @PutMapping()
    public Veiculo alterar(@RequestBody Veiculo v) {
        return veiculoService.salvar(v);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable String id) {
        Optional<Veiculo> v = veiculoService.buscarPorId(id);
        if (v.isPresent()) {
            veiculoService.excluir(v.get());
            return ResponseEntity.ok("Veículo excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado.");
        }
    }

    @GetMapping("/disponiveis")
    public List<Veiculo> buscarPorTipoEQuantidade(
            @RequestParam String tipo,
            @RequestParam double minimo
    ) {
        return veiculoService.buscarPorTipoEQuantidade(tipo, minimo);

        //GET /veiculo/disponiveis?tipo=Carro&minimo=450
    }

    @PutMapping("/depreciar")
    public ResponseEntity<String> depreciarTodosOuPorTipo(
            @RequestParam double taxa,
            @RequestParam(required = false) String tipo
    ) {
        if (taxa <= 0 || taxa >= 1) {
            return ResponseEntity.badRequest().body("Taxa deve estar entre 0 e 1.");
        }

        veiculoService.depreciarVeiculos(taxa, Optional.ofNullable(tipo));
        return ResponseEntity.ok("Depreciação aplicada com sucesso.");

        //PUT http://localhost:8080/veiculo/depreciar?taxa=0.15&tipo=Moto
        //PUT http://localhost:8080/veiculo/depreciar?taxa=0.1
    }

    @PutMapping("/reajustar")
    public ResponseEntity<String> reajustarDiariaTodosOuPorTipo(
            @RequestParam double taxa,
            @RequestParam(required = false) String tipo
    ) {
        if (taxa <= 0) {
            return ResponseEntity.badRequest().body("A taxa deve ser positiva.");
        }

        veiculoService.reajustarDiaria(taxa, Optional.ofNullable(tipo));
        return ResponseEntity.ok("Reajuste aplicado com sucesso.");

        //PUT http://localhost:8080/veiculo/reajustar?taxa=0.15
        //PUT http://localhost:8080/veiculo/reajustar?taxa=0.05&tipo=Caminhao
    }




}

/*
        public static void inserir(Veiculo v) throws SQLException {
            Connection con = null;
            try {
                con = Conexao.getConexao();
            } catch (Exception e) {
                throw new SQLException();
            }
            String sql = "INSERT INTO Veiculo (placa, marca, modelo, ano, diaria, valorBem, atributoExtra, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setInt(4, v.getAnoDeFabricacao());
            ps.setDouble(5, v.getValorDiaria());
            ps.setDouble(6, v.getValorAvaliado());



            double atributoExtra = 0;
            int tipo = 0;

            if (v instanceof Carro) {
                atributoExtra = ((Carro) v).getAutonomia();
                tipo = 2;
            } else if (v instanceof Moto) {
                atributoExtra = ((Moto) v).getCilindrada();
                tipo = 1;
            } else if (v instanceof Onibus) {
                atributoExtra = ((Onibus) v).getCapacidade();
                tipo = 4;
            } else if (v instanceof Caminhao) {
                atributoExtra = ((Caminhao) v).getCarga();
                tipo = 3;
            }

            ps.setDouble(7, atributoExtra);
            ps.setInt(8, tipo);
            ps.execute();
            ps.close();
        }

        public static Veiculo pesquisar(String placa) throws SQLException {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM Veiculo WHERE placa = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, placa);
            ResultSet rs = ps.executeQuery();
            Veiculo v = null;

            if (rs.next()) {
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                int ano = rs.getInt("ano");
                double valorBem = rs.getDouble("valorBem");
                double diaria = rs.getDouble("diaria");
                double atributoExtra = rs.getDouble("atributoExtra");
                int tipo = rs.getInt("tipo");

                switch (tipo) {
                    case 2:
                        v = new Carro(marca, modelo, ano, valorBem, diaria, placa, atributoExtra);
                        break;
                    case 1:
                        v = new Moto(marca, modelo, ano, valorBem, diaria, placa, atributoExtra);
                        break;
                    case 4:
                        v = new Onibus(marca, modelo, ano, valorBem, diaria, placa, (int) atributoExtra);
                        break;
                    case 3:
                        v = new Caminhao(marca, modelo, ano, valorBem, diaria, placa, atributoExtra);
                        break;
                }
            }

            rs.close();
            ps.close();
            return v;
        }

        public static void depreciarValorBemPorTipo(int tipo, double taxa) throws Exception {
            Connection con = Conexao.getConexao();

            // 1. Seleciona todos os veículos do tipo desejado
            String sqlSelect = "SELECT placa, valorBem FROM veiculo WHERE tipo = ?";
            PreparedStatement psSelect = con.prepareStatement(sqlSelect);
            psSelect.setInt(1, tipo);
            ResultSet rs = psSelect.executeQuery();

            // 2. Prepara a query de update
            String sqlUpdate = "UPDATE veiculo SET valorBem = ? WHERE placa = ?";
            PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);

            while (rs.next()) {
                String placa = rs.getString("placa");
                double valorAtual = rs.getDouble("valorBem");
                double novoValor = valorAtual * (1 - taxa);

                psUpdate.setDouble(1, novoValor);
                psUpdate.setString(2, placa);
                psUpdate.executeUpdate();
            }

            rs.close();
            psSelect.close();
            psUpdate.close();
        }

        public static void alterarDiaria(int tipo, double taxaAumento) throws Exception {

            Connection con = Conexao.getConexao();
            String sql = "UPDATE Veiculo SET diaria = ? WHERE tipo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, taxaAumento);
            ps.setInt(2, tipo);
            ps.executeUpdate();
            ps.close();
        }

        public static ArrayList<Veiculo> pesquisarMoto(double cilindrada) throws Exception {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM Veiculo WHERE tipo = '1' AND atributoExtra >= ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, cilindrada);
            ResultSet rs = ps.executeQuery();

            ArrayList<Veiculo> lista = new ArrayList<>();
            while (rs.next()) {
                Veiculo v = new Moto(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getDouble("valorBem"),
                        rs.getDouble("diaria"),
                        rs.getString("placa"),
                        rs.getDouble("atributoExtra")
                );
                lista.add(v);
            }

            rs.close();
            ps.close();
            return lista;
        }

        public static ArrayList<Veiculo> pesquisarCarro(int autonomia) throws Exception {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM Veiculo WHERE tipo = '2' AND atributoExtra >= ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, autonomia);
            ResultSet rs = ps.executeQuery();

            ArrayList<Veiculo> lista = new ArrayList<>();
            while (rs.next()) {
                Veiculo v = new Carro(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getDouble("valorBem"),
                        rs.getDouble("diaria"),
                        rs.getString("placa"),
                        rs.getDouble("atributoExtra")
                );
                lista.add(v);
            }

            rs.close();
            ps.close();
            return lista;
        }

        public static ArrayList<Veiculo> pesquisarOnibus(int capacidade) throws Exception {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM Veiculo WHERE tipo = '4' AND atributoExtra >= ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, capacidade);
            ResultSet rs = ps.executeQuery();

            ArrayList<Veiculo> lista = new ArrayList<>();
            while (rs.next()) {
                Veiculo v = new Onibus(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getDouble("valorBem"),
                        rs.getDouble("diaria"),
                        rs.getString("placa"),
                        (int) rs.getDouble("atributoExtra")
                );
                lista.add(v);
            }

            rs.close();
            ps.close();
            return lista;
        }

        public static ArrayList<Veiculo> pesquisarCaminhao(double carga) throws Exception {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM veiculo WHERE tipo = '3' AND atributoExtra >= ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, carga);
            ResultSet rs = ps.executeQuery();

            ArrayList<Veiculo> lista = new ArrayList<>();
            while (rs.next()) {
                Veiculo v = new Caminhao(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getDouble("valorBem"),
                        rs.getDouble("diaria"),
                        rs.getString("placa"),
                        rs.getDouble("atributoExtra")
                );
                lista.add(v);
            }

            rs.close();
            ps.close();
            return lista;
        }

        public static void removerTodos() throws SQLException {
            Connection con = Conexao.getConexao();
            String sql = "DELETE FROM veiculo";
            PreparedStatement ps = con.prepareStatement(sql);
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new SQLException();
            }
            ps.close();
        }
*/


