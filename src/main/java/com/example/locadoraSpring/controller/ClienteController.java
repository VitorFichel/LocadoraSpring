package com.example.locadoraSpring.controller;
import com.example.locadoraSpring.exceptions.ClienteJaCadastrado;
import com.example.locadoraSpring.exceptions.ClienteNaoCadastrado;
import com.example.locadoraSpring.model.Cliente;
import com.example.locadoraSpring.service.ClienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    private ClienteService ClienteService;

    @GetMapping()
    public List<Cliente> listarTodos() {
        return ClienteService.listarTodos();
    }

    @GetMapping("/{cpf}")
    public Cliente GET(
            @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
            @PathVariable String cpf) throws ClienteNaoCadastrado {
        return ClienteService.buscarCliente(cpf);
    }

    @PostMapping()
    public Cliente POST(@Valid @RequestBody Cliente c) throws ClienteJaCadastrado {
        return ClienteService.inserirCliente(c);
    }

    @PutMapping()
    public Cliente PUT(@Valid @RequestBody Cliente c) throws ClienteNaoCadastrado {
        return ClienteService.alterarCliente(c);
    }

    @DeleteMapping("/{cpf}")
    public Cliente DELETE(
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
        @PathVariable String cpf) throws ClienteNaoCadastrado {
        return ClienteService.excluirCliente(cpf);
    }
}