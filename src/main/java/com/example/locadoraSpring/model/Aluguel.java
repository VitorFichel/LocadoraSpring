package com.example.locadoraSpring.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    private long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "veiculo_placa", nullable = false)
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "cliente_cpf", nullable = false)
    private Cliente cliente;


    @Min(value = 1, message = "numero de dias deve ser maior que zero")
    @Max(value = 365, message = "numero de dias deve ser menor que 365")
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
    @PreUpdate
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


