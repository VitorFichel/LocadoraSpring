package com.example.locadoraSpring.service;

import com.example.locadoraSpring.repository.AluguelRepository;
import com.example.locadoraSpring.exceptions.ClienteNaoCadastrado;
import com.example.locadoraSpring.model.*;
import jakarta.persistence.DiscriminatorValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository daoAluguel;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private ClienteService clienteService;

    public Aluguel buscarAluguelPorPlaca(String placa) {
        return daoAluguel.findByVeiculoPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado para a placa: " + placa));
    }

    public Aluguel registrarAluguel(Aluguel a) throws ClienteNaoCadastrado {
        String placa = a.getVeiculo().getPlaca();
        String cpf = a.getCliente().getCpf();

        // Verifica se veículo está alugado no momento
        if (daoAluguel.existsByVeiculoPlacaAndDataDevolucaoAfter(placa, new Date())) {
            throw new RuntimeException("Veículo está atualmente alugado.");
        }

        // Busca veículo e cliente
        Veiculo veiculo = veiculoService.buscarPorPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com a placa: " + placa));

        Cliente cliente = clienteService.buscarCliente(cpf);

        // Associa entidades ao aluguel
        a.setVeiculo(veiculo);
        a.setCliente(cliente);

        // Salva no banco (a data_devolucao já será calculada com @PrePersist)
        return daoAluguel.save(a);
    }

    public Aluguel registrarDevolucao(String placa) {
        // Busca o aluguel ativo do veículo
        Aluguel aluguel = daoAluguel
                .findByVeiculoPlacaAndDataDevolucaoAfter(placa, new Date())
                .orElseThrow(() -> new RuntimeException("Nenhum aluguel ativo encontrado para a placa: " + placa));

        // Atualiza a data de devolução para agora
        aluguel.setDataDevolucao(new Date());

        // Salva e retorna
        return daoAluguel.save(aluguel);
    }

    public double faturamentoTotalPorTipo(String tipo) {
        List<Aluguel> todosAlugueis = (List<Aluguel>) daoAluguel.findAll();

        return switch (tipo.toLowerCase()) {
            case "moto" -> todosAlugueis.stream()
                    .filter(a -> a.getVeiculo() instanceof Moto)
                    .mapToDouble(Aluguel::getAluguel)
                    .sum();

            case "carro" -> todosAlugueis.stream()
                    .filter(a -> a.getVeiculo() instanceof Carro)
                    .mapToDouble(Aluguel::getAluguel)
                    .sum();

            case "caminhao" -> todosAlugueis.stream()
                    .filter(a -> a.getVeiculo() instanceof Caminhao)
                    .mapToDouble(Aluguel::getAluguel)
                    .sum();

            case "onibus" -> todosAlugueis.stream()
                    .filter(a -> a.getVeiculo() instanceof Onibus)
                    .mapToDouble(Aluguel::getAluguel)
                    .sum();

            default -> throw new IllegalArgumentException("Tipo de veículo inválido: " + tipo);
        };
    }

    public int quantidadeTotalDeDiarias(String tipo) {
        List<Aluguel> alugueis = (List<Aluguel>) daoAluguel.findAll();

        int totalDias = 0;

        for (Aluguel a : alugueis) {
            String tipoVeiculo = a.getVeiculo().getClass().getAnnotation(DiscriminatorValue.class).value();
            if (tipoVeiculo.equalsIgnoreCase(tipo)) {
                totalDias += a.getDias();
            }
        }

        return totalDias;
    }









}

