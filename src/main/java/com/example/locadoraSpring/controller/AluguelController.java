package com.example.locadoraSpring.controller;

import com.example.locadoraSpring.daos.interfaceAluguel;
import com.example.locadoraSpring.model.Aluguel;
import com.example.locadoraSpring.service.AluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("aluguel")
public class AluguelController {

    @Autowired
    private interfaceAluguel daoAluguel;

    @Autowired
    private AluguelService AluguelService;

    @GetMapping("/{placa}")
    public Aluguel buscarPorPlaca(@PathVariable String placa) throws Exception {
        return AluguelService.buscarAluguelPorPlaca(placa);
    }

    @GetMapping()
    public List<Aluguel> listar() throws Exception {
        return (List<Aluguel>) daoAluguel.findAll();
    }

    @PostMapping()
    public Aluguel cadastrar(@RequestBody Aluguel aluguel) throws Exception {
        return AluguelService.registrarAluguel(aluguel);

        /*  http://localhost:8080/aluguel
        {
            "veiculo": { "placa": "ABC1234","tipo_veiculo": "Carro" },
            "cliente": { "cpf": 1234900},
            "dias": 5,
            "dataInicio": "2025-07-03T00:00:00"
        }*/
    }

    @PutMapping()
    public Aluguel alterar(@RequestBody Aluguel a) throws Exception {
        return daoAluguel.save(a);
    }

    @DeleteMapping("/{id}")
    public Optional<Aluguel> excluir(@PathVariable int id) throws Exception {
        Optional<Aluguel> a = daoAluguel.findById(id);
        if (a.isPresent()) {
            daoAluguel.deleteById(id);
        }
        return a;
    }

    @PutMapping("/devolucao/{placa}")
    public Aluguel devolverVeiculo(@PathVariable String placa) {
        return AluguelService.registrarDevolucao(placa);
    }

    @GetMapping("/faturamento/{tipo}")
    public double faturamentoPorTipo(@PathVariable String tipo) {
        return AluguelService.faturamentoTotalPorTipo(tipo);
    }

    @GetMapping("/diarias/{tipo}")
    public int quantidadeTotalDeDiarias(@PathVariable String tipo) {
        return AluguelService.quantidadeTotalDeDiarias(tipo);
    }



}








/*
    public static void inserir(Aluguel a) throws SQLException {
        Connection con = Conexao.getConexao();
        String sql = "INSERT INTO aluguel (id_cliente, id_veiculo, data_inicio, dias) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, a.getCliente().getCpf());
        ps.setString(2, a.getVeiculo().getPlaca());
        ps.setDate(3, new java.sql.Date(a.getDataInicio().getTime()));
        ps.setInt(4, a.getDias());
        try {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("erro ao inserir no SQL");
        }
        ps.close();
    }

    public static boolean veiculoEstaAlugado(String placa) throws VeiculoAlugado, SQLException {
        Connection con = Conexao.getConexao();
        String sql = "SELECT * FROM aluguel WHERE id_veiculo = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, placa);
        ResultSet rs = ps.executeQuery();
        boolean alugado = rs.next();
        rs.close();
        ps.close();
        return alugado;
    }

    public static double faturamentoTotal(int tipo, Date inicio, Date fim) {
        double total = 0.0;

        try {
            Connection con = Conexao.getConexao();

            String tipoCondicao = "";
            if (tipo >= 1 && tipo <= 4) {
                tipoCondicao = "AND v.tipo = ?";
            }

            String sql = "SELECT a.data_inicio, a.data_devolucao, v.diaria " +
                    "FROM aluguel a " +
                    "JOIN veiculo v ON a.id_veiculo = v.placa " +
                    "WHERE a.data_devolucao IS NOT NULL " +
                    "AND a.data_devolucao >= ? AND a.data_devolucao <= ? " +
                    tipoCondicao;

            PreparedStatement ps = con.prepareStatement(sql);

            if (tipo >= 1 && tipo <= 4) {
                ps.setDate(1, new java.sql.Date(inicio.getTime()));
                ps.setDate(2, new java.sql.Date(fim.getTime()));
                ps.setString(3, String.valueOf(tipo));
            } else {
                ps.setDate(1, new java.sql.Date(inicio.getTime()));
                ps.setDate(2, new java.sql.Date(fim.getTime()));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Date dataInicio = rs.getDate("data_inicio");
                Date dataDevolucao = rs.getDate("data_devolucao");
                double diaria = rs.getDouble("diaria");

                if (dataInicio != null && dataDevolucao != null) {
                    long diffMillis = dataDevolucao.getTime() - dataInicio.getTime();
                    int dias = (int) (diffMillis / (1000 * 60 * 60 * 24) -1);
                    total += dias * diaria;
                }
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao calcular faturamento total");
        }

        return total;
    }

    public static int quantidadeTotalDeDiarias(int tipo, Date inicio, Date fim) throws Exception {
        Connection con = Conexao.getConexao();
        String tipoVeiculo = "";

        // Mapear o tipo para string, conforme tua lógica
        switch(tipo) {
            case 1: tipoVeiculo = "1"; break;
            case 2: tipoVeiculo = "2"; break;
            case 3: tipoVeiculo = "3"; break;
            case 4: tipoVeiculo = "4"; break;
            case 0: tipoVeiculo = "0"; break;
        }

        // SQL filtrando por data_inicio ao invés de data_devolucao
        String sql;
        if (tipo == 0) {
            sql = "SELECT data_inicio, data_devolucao FROM aluguel WHERE data_inicio >= ? AND data_inicio <= ?";
        } else {
            sql = "SELECT a.data_inicio, a.data_devolucao FROM aluguel a JOIN Veiculo v ON a.id_veiculo = v.placa WHERE v.tipo = ? AND a.data_inicio >= ? AND a.data_inicio <= ?";
        }

        PreparedStatement ps = con.prepareStatement(sql);

        if (tipo == 0) {
            ps.setDate(1, new java.sql.Date(inicio.getTime()));
            ps.setDate(2, new java.sql.Date(fim.getTime()));
        } else {
            ps.setString(1, tipoVeiculo);
            ps.setDate(2, new java.sql.Date(inicio.getTime()));
            ps.setDate(3, new java.sql.Date(fim.getTime()));
        }

        ResultSet rs = ps.executeQuery();

        int total = 0;

        while (rs.next()) {
            Date dataInicioAluguel = rs.getDate("data_inicio");
            Date dataDevolucao = rs.getDate("data_devolucao");

            if (dataDevolucao != null) {
                // Cálculo da diferença em dias
                long diffMillis = dataDevolucao.getTime() - dataInicioAluguel.getTime();
                int dias = (int)(diffMillis / (1000 * 60 * 60 * 24));
                total += dias;
            }
        }

        rs.close();
        ps.close();

        return total;
    }

    public static Aluguel buscarAluguelPorCpf(int cpf) throws Exception {
        Connection con = Conexao.getConexao();
        String sql = "SELECT * FROM aluguel WHERE id_cliente = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, cpf);
        ResultSet rs = ps.executeQuery();

        Aluguel aluguel = null;
        if (rs.next()) {
            String placa = rs.getString("id_veiculo");
            Date dataInicio = rs.getDate("data_inicio");

            Veiculo v = VeiculoController.pesquisar(placa); // Você já tem esse método
            Cliente c = ClienteController.pesquisar(String.valueOf(cpf)); // Também já tem esse

            int dias = rs.getInt("dias");
            aluguel = new Aluguel(v, c, dataInicio, dias);
        }

        rs.close();
        ps.close();
        return aluguel;
    }

    public static boolean atualizarDataDevolucao(int cpf, String placa, Date dataDevolucao) throws Exception {
        Connection con = Conexao.getConexao();
        String sql = "UPDATE aluguel SET data_devolucao = ? WHERE id_cliente = ? AND id_veiculo = ? AND data_devolucao IS NULL";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(dataDevolucao.getTime()));
        ps.setInt(2, cpf);
        ps.setString(3, placa);
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        return rowsUpdated > 0;
    }

    public static void removerUmAluguel(int cpf) throws Exception {
        Connection con = Conexao.getConexao();
        String sql = "DELETE FROM aluguel WHERE id_cliente = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, cpf);
        ps.executeUpdate();
        ps.close();
    }

    public static void removerTodos() throws SQLException {

        try {
            Connection con = Conexao.getConexao();
            String sql = "DELETE FROM aluguel";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new SQLException("não executou a remoção no SQL");
        }

    }
*/



