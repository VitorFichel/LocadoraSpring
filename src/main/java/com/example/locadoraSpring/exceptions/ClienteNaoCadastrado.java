package com.example.locadoraSpring.exceptions;

public class ClienteNaoCadastrado extends Exception {
    public ClienteNaoCadastrado() {
        super("Cliente não cadastrado");
    }
}
