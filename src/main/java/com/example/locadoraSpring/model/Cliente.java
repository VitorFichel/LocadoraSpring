
package com.example.locadoraSpring.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    @Column(name = "cpf", length = 12, nullable = true)
    private Integer cpf;

    @Column(name = "nome", length = 45, nullable = true)
    private String nome;

}
