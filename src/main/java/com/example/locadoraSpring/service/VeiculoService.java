package com.example.locadoraSpring.service;

import com.example.locadoraSpring.repository.VeiculoRepository;
import com.example.locadoraSpring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository daoVeiculo;


    public List<Veiculo> listarTodos() {
        return (List<Veiculo>) daoVeiculo.findAll();
    }

    public Veiculo salvar(Veiculo v) {
        return daoVeiculo.save(v);
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return daoVeiculo.findById(placa);
    }

    public void excluir(Veiculo v) {
        daoVeiculo.delete(v);
    }

    public List<Veiculo> buscarPorTipoEQuantidade(String tipo, double minimo) {
        List<Veiculo> todos = (List<Veiculo>) daoVeiculo.findAll();
        List<Veiculo> filtrados = new ArrayList<>();

        for (Veiculo v : todos) {
            if (v.getClass().getSimpleName().equalsIgnoreCase(tipo)) {
                switch (tipo.toLowerCase()) {
                    case "carro":
                        if (((Carro) v).getAutonomia() >= minimo) {
                            filtrados.add(v);
                        }
                        break;
                    case "caminhao":
                        if (((Caminhao) v).getCarga() >= minimo) {
                            filtrados.add(v);
                        }
                        break;
                    case "onibus":
                        if (((Onibus) v).getCapacidade() >= minimo) {
                            filtrados.add(v);
                        }
                        break;
                    case "moto":
                        if (((Moto) v).getCilindrada() >= minimo) {
                            filtrados.add(v);
                        }
                        break;
                }
            }
        }

        return filtrados;
    }

    public void depreciarVeiculos(double taxa, Optional<String> tipoOpcional) {
        List<Veiculo> todos = (List<Veiculo>) daoVeiculo.findAll();

        for (Veiculo v : todos) {
            if (tipoOpcional.isEmpty() || v.getClass().getSimpleName().equalsIgnoreCase(tipoOpcional.get())) {
                v.diminuirValorBem(taxa);
                daoVeiculo.save(v);
            }
        }
    }

    public void reajustarDiaria(double taxa, Optional<String> tipoOpcional) {
        List<Veiculo> todos = (List<Veiculo>) daoVeiculo.findAll();

        for (Veiculo v : todos) {
            if (tipoOpcional.isEmpty() || v.getClass().getSimpleName().equalsIgnoreCase(tipoOpcional.get())) {
                v.aumentarDiaria(taxa);
                daoVeiculo.save(v);
            }
        }
    }

}
