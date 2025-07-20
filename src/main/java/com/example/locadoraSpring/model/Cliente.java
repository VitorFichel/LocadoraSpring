
package com.example.locadoraSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.processing.Pattern;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @NotBlank
    @Size(min = 1, max = 11, message = "cpf deve ter 11 caracteres")
    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @NotBlank
    @Column(name = "nome", length = 45, nullable = true)
    private String nome;

    @OneToMany(mappedBy = "cliente")
    private List<Aluguel> aluguelList;

}
