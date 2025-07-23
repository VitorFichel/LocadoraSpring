package com.example.locadoraSpring.service;

import com.example.locadoraSpring.repository.ClienteRepository;
import com.example.locadoraSpring.exceptions.ClienteJaCadastrado;
import com.example.locadoraSpring.exceptions.ClienteNaoCadastrado;
import com.example.locadoraSpring.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository daoCliente;

    public List<Cliente> listarTodos() {
        return (List<Cliente>) daoCliente.findAll();
    }

    public Cliente inserirCliente(Cliente c) throws ClienteJaCadastrado {
        if (daoCliente.existsById(c.getCpf())) {
            throw new ClienteJaCadastrado("CPF já cadastrado");
        }
        return daoCliente.save(c);
    }

    public Cliente buscarCliente(String cpf) throws ClienteNaoCadastrado {
        return daoCliente.findByCpf(cpf).orElseThrow(ClienteNaoCadastrado::new);
    }

    public Cliente alterarCliente(Cliente c) throws ClienteNaoCadastrado {
        if (daoCliente.existsById(c.getCpf())) {
            return daoCliente.save(c);
        }
        throw new ClienteNaoCadastrado();
    }

    public Cliente excluirCliente(String cpf) throws ClienteNaoCadastrado {
        if (daoCliente.existsById(cpf)) {
        daoCliente.deleteById(cpf);}
        throw new ClienteNaoCadastrado();
    }

}


