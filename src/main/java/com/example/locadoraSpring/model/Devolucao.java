package com.example.locadoraSpring.model;
import java.util.Date;

public class Devolucao {
    private Veiculo veiculo;
    private Cliente cliente;
    private Date dataFinal;

    public Devolucao(Veiculo veiculo, Date dataFinal, Cliente cliente) {
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.dataFinal = dataFinal;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Date getDataInicio() {
        return dataFinal;
    }
}
