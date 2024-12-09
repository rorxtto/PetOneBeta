package app.petone.controller;

import app.petone.DTO.v2.DTOs.VeterinarioDTO;
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
    private AuthService authService;

    // Lista todos os veterinários
    @GetMapping
    public ResponseEntity<List<VeterinarioDTO>> listarTodos() {
        List<VeterinarioDTO> veterinarios = veterinarioService.listarTodos();
        return ResponseEntity.ok(veterinarios);
    }

    // Busca um veterinário por ID
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioDTO> buscarPorId(@PathVariable Long id) {
        VeterinarioDTO veterinario = veterinarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(veterinario);
    }

    // Cria um novo veterinário
    @PostMapping
    public ResponseEntity<VeterinarioDTO> criarVeterinario(@RequestBody Veterinario veterinario) {
        VeterinarioDTO novoVeterinario = veterinarioService.criarVeterinario(veterinario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoVeterinario);
    }

    // Atualiza os dados de um veterinário
    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioDTO> atualizarVeterinario(@PathVariable Long id, @RequestBody Veterinario dadosAtualizados) {
        VeterinarioDTO veterinarioAtualizado = veterinarioService.atualizarVeterinario(id, dadosAtualizados);
        return ResponseEntity.ok(veterinarioAtualizado);
    }

    // Remove um veterinário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerVeterinario(@PathVariable Long id) {
        veterinarioService.removerVeterinario(id);
        return ResponseEntity.noContent().build();
    }

    // Busca o veterinário logado
    @GetMapping("/me")
    public ResponseEntity<VeterinarioDTO> buscarVeterinarioLogado() {
        String email = authService.getEmailFromToken();
        VeterinarioDTO veterinario = veterinarioService.buscarVeterinarioLogado(email);
        return ResponseEntity.ok(veterinario);
    }
}
