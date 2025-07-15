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
@DiscriminatorValue("Caminhao")
public class Caminhao extends Veiculo{

    double carga;

    public Caminhao(String marc, String mod, int ano, double bem, double diar, String pl, double carg){
        super(marc,mod,ano, bem, diar, pl);
        carga = carg;
    }

    @Override
    public double seguro() {
        return (valorBem*0.08)/365;
    }

}
