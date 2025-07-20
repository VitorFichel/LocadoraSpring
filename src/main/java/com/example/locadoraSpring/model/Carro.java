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
@DiscriminatorValue("Carro")
public class Carro extends Veiculo{

    double autonomia;

    public Carro(String marc, String mod, int ano, double bem, double diar, String pl, double eco){
        super(marc,mod,ano, bem, diar, pl);
        autonomia = eco;
    }


    @Override
    public double seguro() {
        return (getValorBem() *0.03)/365;
    }

}
