package com.example.locadoraSpring.service;

import com.example.locadoraSpring.daos.interfaceCliente;
import com.example.locadoraSpring.exceptions.ClienteJaCadastrado;
import com.example.locadoraSpring.exceptions.ClienteNaoCadastrado;
import com.example.locadoraSpring.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private interfaceCliente daoCliente;

    public List<Cliente> listarTodos() {
        return (List<Cliente>) daoCliente.findAll();
    }

    public Cliente inserirCliente(Cliente c) throws ClienteJaCadastrado {
        if (daoCliente.existsById(c.getCpf())) {
            throw new ClienteJaCadastrado("CPF já cadastrado");
        }
        return daoCliente.save(c);
    }

    public Cliente buscarCliente(Integer cpf) throws ClienteNaoCadastrado {
        return daoCliente.findById(cpf).orElseThrow(ClienteNaoCadastrado::new);
    }

    public Cliente alterarCliente(Cliente c) throws ClienteNaoCadastrado {
        if (daoCliente.existsById(c.getCpf())) {
            return daoCliente.save(c);
        }
        throw new ClienteNaoCadastrado();
    }

    public Cliente excluirCliente(Integer cpf) throws ClienteNaoCadastrado {
        if (daoCliente.existsById(cpf)) {
        daoCliente.deleteById(cpf);}
        throw new ClienteNaoCadastrado();
    }

}


