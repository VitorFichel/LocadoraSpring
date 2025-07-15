package com.example.locadoraSpring.exceptions;

public class VeiculoNaoAlugado extends Exception {
    public VeiculoNaoAlugado() {
        super("Veículo não alugado");
    }
}
