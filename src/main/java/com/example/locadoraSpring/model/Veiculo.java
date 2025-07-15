package com.example.locadoraSpring.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo_veiculo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Carro.class, name = "Carro"),
        @JsonSubTypes.Type(value = Moto.class, name = "Moto"),
        @JsonSubTypes.Type(value = Caminhao.class, name = "Caminhao"),
        @JsonSubTypes.Type(value = Onibus.class, name = "Onibus")
})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_veiculo", discriminatorType = DiscriminatorType.STRING)
public abstract class Veiculo {

    @Id
    @Column(name = "placa",  length = 20, nullable = false)
    String placa;

    @Column(name = "marca",  length = 100, nullable = false)
    String marca;

    @Column(name = "modelo",  length = 100, nullable = false)
    String modelo;

    @Column(name = "ano",  length = 4, nullable = false)
    int ano;

    @Column(name = "diaria",  length = 100, nullable = false)
    double diaria;

    @Column(name = "valorBem",  length = 100, nullable = false)
    double valorBem;

    public Veiculo(String marca, String modelo, int ano, double valorBem, double diaria, String placa) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.valorBem = valorBem;
        this.diaria = diaria;
        this.placa = placa;
    }


    public abstract double seguro();

    public void aumentarDiaria(double taxa){this.diaria *= (1+taxa);}
    public void diminuirValorBem(double taxa){this.valorBem *= (1-taxa);}

}
