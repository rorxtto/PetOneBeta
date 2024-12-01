package app.petone.controller;

import app.petone.DTO.v2.DTOs.PacienteDTO;
import app.petone.model.Paciente;
import app.petone.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarPacientes() {
        List<PacienteDTO> pacientes = pacienteService.getAllPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public PacienteDTO getPacienteById(@PathVariable Long id) {
        return pacienteService.getPacienteById(id);
    }

    /*
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", ""); // Extrai o token do cabeçalho
        List<Paciente> pacientes = pacienteService.listarPacientesDoTutor(token);
        return ResponseEntity.ok(pacientes);
    }
     */

    @PostMapping
    public ResponseEntity<String> adicionarPaciente(
            @RequestBody Paciente paciente,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Paciente pacienteCriado = pacienteService.adicionarPaciente(token, paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body("CRIADO!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> atualizarPaciente(
            @PathVariable Long id,
            @RequestBody Paciente pacienteAtualizado,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        PacienteDTO paciente = pacienteService.atualizarPaciente(token, id, pacienteAtualizado);
        return ResponseEntity.ok(paciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPaciente(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        pacienteService.removerPaciente(token, id);
        return ResponseEntity.noContent().build();
    }
}

