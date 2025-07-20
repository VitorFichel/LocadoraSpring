package com.example.locadoraSpring.controller;
import com.example.locadoraSpring.model.Veiculo;
import com.example.locadoraSpring.service.VeiculoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping()
    public List<Veiculo> listarTodos() {
        return veiculoService.listarTodos();
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Veiculo> buscarPorPlaca(
        @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "Placa deve estar no formato brasileiro válido")
        @PathVariable String placa) {
        return veiculoService.buscarPorPlaca(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Veiculo cadastrar(@Valid @RequestBody Veiculo v) {
        return veiculoService.salvar(v);
    }

    @PutMapping()
    public Veiculo alterar(@Valid @RequestBody Veiculo v) {
        return veiculoService.salvar(v);
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<String> excluir(
        @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "Placa deve estar no formato brasileiro válido")
        @PathVariable String placa) {
        Optional<Veiculo> v = veiculoService.buscarPorPlaca(placa);
        if (v.isPresent()) {
            veiculoService.excluir(v.get());
            return ResponseEntity.ok("Veículo excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado.");
        }
    }

    @GetMapping("/disponiveis")
    public List<Veiculo> buscarPorTipoEQuantidade(
            @RequestParam String tipo,
            @RequestParam double minimo
    ) {
        return veiculoService.buscarPorTipoEQuantidade(tipo, minimo);

        //GET /veiculo/disponiveis?tipo=Carro&minimo=450
    }

    @PutMapping("/depreciar")
    public ResponseEntity<String> depreciarTodosOuPorTipo(
            @RequestParam double taxa,
            @RequestParam(required = false) String tipo
    ) {
        if (taxa <= 0 || taxa >= 1) {
            return ResponseEntity.badRequest().body("Taxa deve estar entre 0 e 1.");
        }

        veiculoService.depreciarVeiculos(taxa, Optional.ofNullable(tipo));
        return ResponseEntity.ok("Depreciação aplicada com sucesso.");

        //PUT http://localhost:8080/veiculo/depreciar?taxa=0.15&tipo=Moto
        //PUT http://localhost:8080/veiculo/depreciar?taxa=0.1
    }

    @PutMapping("/reajustar")
    public ResponseEntity<String> reajustarDiariaTodosOuPorTipo(
            @RequestParam double taxa,
            @RequestParam(required = false) String tipo
    ) {
        if (taxa <= 0) {
            return ResponseEntity.badRequest().body("A taxa deve ser positiva.");
        }

        veiculoService.reajustarDiaria(taxa, Optional.ofNullable(tipo));
        return ResponseEntity.ok("Reajuste aplicado com sucesso.");

        //PUT http://localhost:8080/veiculo/reajustar?taxa=0.15
        //PUT http://localhost:8080/veiculo/reajustar?taxa=0.05&tipo=Caminhao
    }


}