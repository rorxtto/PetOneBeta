package app.petone.controller;

import app.petone.model.Procedimento;
import app.petone.service.ProcedimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procedimentos")
public class ProcedimentoController {
    @Autowired
    private ProcedimentoService procedimentoService;

    // Endpoint para listar todos os procedimentos
    @GetMapping
    public ResponseEntity<List<Procedimento>> listarProcedimentos() {
        List<Procedimento> procedimentos = procedimentoService.listarTodos();
        return ResponseEntity.ok(procedimentos);
    }

    // Endpoint para buscar um procedimento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Procedimento> buscarPorId(@PathVariable Long id) {
        Procedimento procedimento = procedimentoService.buscarPorId(id);
        return ResponseEntity.ok(procedimento);
    }

    // Endpoint para criar um novo procedimento
    @PostMapping
    public ResponseEntity<Procedimento> criarProcedimento(@RequestBody Procedimento procedimento) {
        Procedimento procedimentoCriado = procedimentoService.criarProcedimento(procedimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(procedimentoCriado);
    }

    // Endpoint para atualizar um procedimento
    @PutMapping("/{id}")
    public ResponseEntity<Procedimento> atualizarProcedimento(
            @PathVariable Long id,
            @RequestBody Procedimento procedimentoAtualizado) {
        Procedimento procedimento = procedimentoService.atualizarProcedimento(id, procedimentoAtualizado);
        return ResponseEntity.ok(procedimento);
    }

    // Endpoint para remover um procedimento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProcedimento(@PathVariable Long id) {
        procedimentoService.removerProcedimento(id);
        return ResponseEntity.noContent().build();
    }
}

