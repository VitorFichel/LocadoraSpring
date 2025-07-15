package com.example.locadoraSpring.daos;

import com.example.locadoraSpring.model.Aluguel;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface interfaceAluguel extends CrudRepository<Aluguel, Integer> {

    Optional<Aluguel> findByVeiculoPlaca(String placa);
    Optional<Aluguel> findByClienteCpf(Integer cpf);
    Optional<Aluguel> findByVeiculoPlacaAndDataDevolucaoAfter(String placa, Date agora);
    boolean existsByVeiculoPlacaAndDataDevolucaoAfter(String placa, Date now);


}
