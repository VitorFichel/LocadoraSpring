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
@DiscriminatorValue("Moto")
public class Moto extends Veiculo{

    double cilindrada;

    public Moto (String marc, String mod, int ano, double bem, double diar, String pl,  double cilindrada) {
        super(marc, mod, ano, bem, diar, pl);
        this.cilindrada = cilindrada;
    }
    @Override
    public double seguro() {
        return (getValorBem() *0.11)/365;
    }

}
