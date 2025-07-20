package com.example.locadoraSpring.controller;

import com.example.locadoraSpring.repository.AluguelRepository;
import com.example.locadoraSpring.model.Aluguel;
import com.example.locadoraSpring.service.AluguelService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("aluguel")
public class AluguelController {

    @Autowired
    private AluguelRepository daoAluguel;

    @Autowired
    private AluguelService AluguelService;

    @GetMapping("/{placa}")
    public Aluguel buscarPorPlaca(
            @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "Placa deve estar no formato brasileiro válido")
            @PathVariable String placa) throws Exception {
            return AluguelService.buscarAluguelPorPlaca(placa);
    }

    @GetMapping()
    public List<Aluguel> listarTodos() throws Exception {
        return (List<Aluguel>) daoAluguel.findAll();
    }

    @PostMapping()
    public Aluguel cadastrar(@Valid @RequestBody Aluguel aluguel) throws Exception {
        return AluguelService.registrarAluguel(aluguel);
        /*  http://localhost:8080/aluguel
        {
            "veiculo": { "placa": "ABC1234","tipo_veiculo": "Carro" },
            "cliente": { "cpf": 1234900},
            "dias": 5,
            "dataInicio": "2025-07-03T00:00:00"
        }*/
    }

    @PutMapping()
    public Aluguel alterar(@Valid @RequestBody Aluguel a) throws Exception {
        return daoAluguel.save(a);
    }

    @DeleteMapping("/{placa}")
    public Optional<Aluguel> excluir(@PathVariable String placa) throws Exception {
        Optional<Aluguel> a = daoAluguel.findByVeiculoPlaca(placa);
        if (a.isPresent()) {
            daoAluguel.delete(a.get());
        }
        return a;
    }

    @PutMapping("/devolucao/{placa}")
    public Aluguel devolverVeiculo(
            @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "Placa deve estar no formato brasileiro válido")
            @PathVariable String placa) {
            return AluguelService.registrarDevolucao(placa);
    }

    @GetMapping("/faturamento/{tipo}")
    public double faturamentoPorTipo(
            @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "Placa deve estar no formato brasileiro válido")
            @PathVariable String tipo) {
            return AluguelService.faturamentoTotalPorTipo(tipo);
    }

    @GetMapping("/diarias/{tipo}")
    public int quantidadeTotalDeDiarias(
            @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "Placa deve estar no formato brasileiro válido")
            @PathVariable String tipo) {
        return AluguelService.quantidadeTotalDeDiarias(tipo);
    }



}