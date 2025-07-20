package com.example.locadoraSpring.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DiscriminatorValue("Onibus")
public class Onibus extends Veiculo{

    double capacidade;

    public Onibus(String marc, String mod, int ano, double bem, double diar, String pl, int capac){
        super(marc,mod,ano, bem, diar, pl);
        capacidade = capac;
    }

    @Override
    public double seguro() {
        return (getValorBem() *0.20)/365;
    }

}
