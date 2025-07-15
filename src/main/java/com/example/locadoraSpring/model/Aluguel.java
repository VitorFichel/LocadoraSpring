package com.example.locadoraSpring.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private int dias;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_inicio", nullable = false)
    private Date dataInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_devolucao", nullable = true)
    private Date dataDevolucao;

    @Column(name = "aluguel",  length = 100, nullable = false)
    private double aluguel;

    @PrePersist
    public void prepararPersistencia() {
        if (dataInicio != null && dias > 0) {
            // Calcula data de devolução
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataInicio);
            cal.add(Calendar.DATE, dias);
            this.dataDevolucao = cal.getTime();
        }

        if (veiculo != null && dias > 0) {
            // Calcula valor do aluguel
            this.aluguel = (veiculo.getDiaria() + veiculo.seguro()) * dias;
        } else {
            this.aluguel = 0.0;
        }
    }


}


