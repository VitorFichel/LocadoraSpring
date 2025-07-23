package com.example.locadoraSpring.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "veiculo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_veiculo", discriminatorType = DiscriminatorType.STRING)
public abstract class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Placa é obrigatória")
    @Size(min = 1, max = 8)
    @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "Placa deve estar no formato brasileiro válido")
    @Column(name = "placa",  length = 8, nullable = false)
    private String placa;

    @NotBlank(message = "Marca é obrigatória")
    @Size(min = 1, max = 50, message = "Marca deve ter entre 1 e 50 caracteres")
    @Column(name = "marca",  length = 50, nullable = false)
    private String marca;

    @NotBlank(message = "Modelo é obrigatório")
    @Size(min = 1, max = 50, message = "Modelo deve ter entre 1 e 50 caracteres")
    @Column(name = "modelo",  length = 50, nullable = false)
    private String modelo;

    @Min(value = 1900, message = "Ano deve ser a partir de 1900")
    @Max(value = 2030, message = "Ano deve ser até que 2030")
    @Column(name = "ano",  length = 4, nullable = false)
    private int ano;

    @DecimalMin(value = "0.01", message = "Diária deve ser maior que zero")
    @Column(name = "diaria", nullable = false)
    private double diaria;

    @DecimalMin(value = "0.01", message = "Valor do bem deve ser maior que zero")
    @Column(name = "valorBem", length = 100, nullable = false)
    private double valorBem;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Aluguel> alugueis =  new ArrayList<>();

    public Veiculo(String marca, String modelo, int ano, double valorBem, double diaria, String placa) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.valorBem = valorBem;
        this.diaria = diaria;
        this.placa = placa;
        this.alugueis = new ArrayList<>();

    }


    public abstract double seguro();

    public void aumentarDiaria(double taxa){this.diaria *= (1+taxa);}
    public void diminuirValorBem(double taxa){this.valorBem *= (1-taxa);}

}
