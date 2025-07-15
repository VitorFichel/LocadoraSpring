package com.example.locadoraSpring.daos;

import com.example.locadoraSpring.model.Veiculo;
import org.springframework.data.repository.CrudRepository;

public interface interfaceVeiculo extends CrudRepository<Veiculo, String> {
}
