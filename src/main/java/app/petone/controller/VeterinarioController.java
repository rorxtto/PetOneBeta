package app.petone.controller;

import app.petone.auth.service.AuthService;
import app.petone.model.Veterinario;
import app.petone.service.VeterinarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    @Autowired
    private AuthService authService; // Para extrair o email do token

    // Lista todos os veterinários
    @GetMapping
    public ResponseEntity<List<Veterinario>> listarTodos() {
        List<Veterinario> veterinarios = veterinarioService.listarTodos();
        return ResponseEntity.ok(veterinarios);
    }

    // Busca um veterinário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Veterinario> buscarPorId(@PathVariable Long id) {
        Veterinario veterinario = veterinarioService.buscarPorId(id);
        return ResponseEntity.ok(veterinario);
    }

    // Cria um novo veterinário
    @PostMapping
    public ResponseEntity<Veterinario> criarVeterinario(@RequestBody Veterinario veterinario) {
        Veterinario novoVeterinario = veterinarioService.criarVeterinario(veterinario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoVeterinario);
    }

    // Atualiza os dados de um veterinário
    @PutMapping("/{id}")
    public ResponseEntity<Veterinario> atualizarVeterinario(
            @PathVariable Long id,
            @RequestBody Veterinario dadosAtualizados) {
        Veterinario veterinarioAtualizado = veterinarioService.atualizarVeterinario(id, dadosAtualizados);
        return ResponseEntity.ok(veterinarioAtualizado);
    }

    // Remove um veterinário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerVeterinario(@PathVariable Long id) {
        veterinarioService.removerVeterinario(id);
        return ResponseEntity.noContent().build();
    }

    // Busca o veterinário logado (com base no JWT)
    @GetMapping("/me")
    public ResponseEntity<Veterinario> buscarVeterinarioLogado() {
        String email = authService.getEmailFromToken();
        Veterinario veterinario = veterinarioService.buscarVeterinarioLogado(email);
        return ResponseEntity.ok(veterinario);
    }
}

