package app.petone.service;

import app.petone.auth.service.AuthService;
import app.petone.model.Tutor;
import app.petone.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AuthService authService; // Para extrair informações do JWT

    // Busca o perfil do tutor logado (evitando recursão infinita)
    public Tutor buscarPerfilCompleto(String email) {
        return tutorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado"));
    }

    // Lista todos os tutores (opcional, para administração)
    public List<Tutor> listarTodos() {
        return tutorRepository.findAll();
    }

    // Atualiza os dados de um tutor
    public Tutor atualizarTutor(Long id, Tutor dadosAtualizados) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado"));
        tutor.setFirstName(dadosAtualizados.getFirstName());
        tutor.setLastName(dadosAtualizados.getLastName());
        tutor.setUsername(dadosAtualizados.getUsername());
        tutor.setRole(dadosAtualizados.getRole());
        return tutorRepository.save(tutor);
    }
}
