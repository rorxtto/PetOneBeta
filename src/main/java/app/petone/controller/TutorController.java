package app.petone.controller;

import app.petone.DTO.TutorDTO;
import app.petone.auth.service.AuthService;
import app.petone.model.Tutor;
import app.petone.service.TutorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private AuthService authService; // Para extrair o email do token

    // Retorna o perfil do tutor logado
    @GetMapping("/me")
    public ResponseEntity<TutorDTO> buscarPerfilCompleto() {
        System.out.println("k");
        String email = authService.getEmailFromToken();
        Tutor tutor = tutorService.buscarPerfilCompleto(email);
        TutorDTO tutorDTO = new TutorDTO(tutor); // Converter para DTO para evitar recursão infinita
        return ResponseEntity.ok(tutorDTO);
    }

    // Lista todos os tutores (opcional, para administração)
    @GetMapping
    public ResponseEntity<List<TutorDTO>> listarTodos() {
        List<Tutor> tutores = tutorService.listarTodos();
        List<TutorDTO> tutorDTOs = tutores.stream().map(TutorDTO::new).toList();
        return ResponseEntity.ok(tutorDTOs);
    }

    // Atualiza os dados do tutor
    @PutMapping("/{id}")
    public ResponseEntity<Tutor> atualizarTutor(
            @PathVariable Long id,
            @RequestBody Tutor dadosAtualizados) {
        Tutor tutorAtualizado = tutorService.atualizarTutor(id, dadosAtualizados);
        return ResponseEntity.ok(tutorAtualizado);
    }
}

