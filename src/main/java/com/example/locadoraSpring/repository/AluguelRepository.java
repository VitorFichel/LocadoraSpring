package com.example.locadoraSpring.repository;

import com.example.locadoraSpring.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Integer> {

    Optional<Aluguel> findByVeiculoPlaca(String placa);
    Optional<Aluguel> findByVeiculoPlacaAndDataDevolucaoAfter(String placa, Date agora);
    boolean existsByVeiculoPlacaAndDataDevolucaoAfter(String placa, Date now);


}
