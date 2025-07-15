package com.example.locadoraSpring.exceptions;

public class VeiculoNaoCadastrado extends Exception {
    public VeiculoNaoCadastrado(){
        super("Veiculo não cadastrado");
    }
}
