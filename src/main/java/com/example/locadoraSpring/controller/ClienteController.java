package com.example.locadoraSpring.controller;
import com.example.locadoraSpring.exceptions.ClienteJaCadastrado;
import com.example.locadoraSpring.exceptions.ClienteNaoCadastrado;
import com.example.locadoraSpring.model.Cliente;
import com.example.locadoraSpring.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    private ClienteService ClienteService;

    @GetMapping()
    public List<Cliente> listarTodos() {
        return ClienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Cliente GET(@PathVariable int id) throws ClienteNaoCadastrado {
        return ClienteService.buscarCliente(id);
    }

    @PostMapping()
    public Cliente POST(@RequestBody Cliente c) throws ClienteJaCadastrado {
        return ClienteService.inserirCliente(c);
    }

    @PutMapping()
    public Cliente PUT(@RequestBody Cliente c) throws ClienteNaoCadastrado {
        return ClienteService.alterarCliente(c);
    }

    @DeleteMapping("/{id}")
    public Cliente DELETE(@PathVariable int id) throws ClienteNaoCadastrado {
        return ClienteService.excluirCliente(id);
    }
}

/*
    public static void inserir(Cliente c) throws SQLException {
        Connection con = Conexao.getConexao();
        String sql = "INSERT INTO Cliente (cpf, nome) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, c.getCpf());
        ps.setString(2, c.getNome());
        System.out.println("QUERY: " + sql);
        try {
            ps.execute();
        } catch (SQLException e) {
            throw new SQLException();
        }
        ps.close();
    }

    public static Cliente pesquisar(String cpf) throws Exception {
        Connection con = Conexao.getConexao();
        String sql = "SELECT * FROM Cliente WHERE cpf = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,cpf);
        ResultSet rs = ps.executeQuery();
        Cliente c = null;
        if (rs.next()) {
            c = new Cliente(rs.getInt("cpf"), rs.getString("nome"));
        }
        rs.close();
        ps.close();
        return c;
    }

    public void atualizar(Cliente c) throws Exception {
        Connection con = Conexao.getConexao();
        String sql = "UPDATE Cliente SET nome = ? WHERE cpf = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, c.getNome());
        ps.setInt(2, c.getCpf());
        ps.executeUpdate();
        ps.close();
    }

    public void deletar(int cpf) throws Exception {
        Connection con = Conexao.getConexao();
        String sql = "DELETE FROM Cliente WHERE cpf = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, cpf);
        ps.execute();
        ps.close();
    }

    public ArrayList<Cliente> listarTodos() throws Exception {
        Connection con = Conexao.getConexao();
        String sql = "SELECT * FROM Cliente";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Cliente> lista = new ArrayList<>();
        while (rs.next()) {
            Cliente c = new Cliente(rs.getInt("cpf"), rs.getString("nome"));
            lista.add(c);
        }
        rs.close();
        ps.close();
        return lista;
    }

    public static void removerTodos() throws SQLException {
        Connection con = Conexao.getConexao();
        String sql = "DELETE FROM cliente";
        PreparedStatement ps = con.prepareStatement(sql);
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
        ps.close();
    }

*/
