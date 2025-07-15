package com.example.locadoraSpring.daos;

import com.example.locadoraSpring.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface interfaceCliente extends CrudRepository<Cliente, Integer> {
}
