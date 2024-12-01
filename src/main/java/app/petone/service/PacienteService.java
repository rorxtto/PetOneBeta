package app.petone.service;

import app.petone.DTO.v2.DTOs.PacienteDTO;
import app.petone.DTO.v2.mapper.PacienteMapper;
import app.petone.auth.service.AuthService;
import app.petone.model.Paciente;
import app.petone.model.Tutor;
import app.petone.repository.PacienteRepository;
import app.petone.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AuthService authService;

    public List<PacienteDTO> getAllPacientes() {
        String email = authService.getEmailFromToken();
        Optional<Tutor> tutor = tutorRepository.findByEmail(email);
        List<Paciente> pacientes = pacienteRepository.findAllByTutor(tutor);
        return pacientes.stream()
                .map(PacienteMapper::toDTO)  // Usando o mapper para converter para DTO
                .collect(Collectors.toList());
    }

    public PacienteDTO getPacienteById(Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.map(PacienteMapper::toDTO).orElse(null);  // Garantir que estamos retornando o DTO
    }

    // Lista todos os pacientes do Tutor autenticado
    public List<Paciente> listarPacientesDoTutor(String token) {
        String email = authService.getEmailFromToken();
        Tutor tutor = tutorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado"));

        return pacienteRepository.findAllByTutor(Optional.ofNullable(tutor));
    }

    // Adiciona um novo paciente para o Tutor autenticado
    public Paciente adicionarPaciente(String token, Paciente paciente) {
        String email = authService.getEmailFromToken();
        Tutor tutor = tutorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado"));

        paciente.setTutor(tutor); // Vincula o paciente ao Tutor
        return pacienteRepository.save(paciente);
    }

    // Atualiza informações de um paciente do Tutor autenticado
    public PacienteDTO atualizarPaciente(String token, Long pacienteId, Paciente pacienteAtualizado) {
        // Verifica se o paciente existe
        Optional<Paciente> pacienteExistente = pacienteRepository.findById(pacienteId);
        if (pacienteExistente.isPresent()) {
            Paciente paciente = pacienteExistente.get();

            // Atualiza os dados do paciente
            paciente.setNome(pacienteAtualizado.getNome());
            paciente.setDataNascimento(pacienteAtualizado.getDataNascimento());
            paciente.setEspecie(pacienteAtualizado.getEspecie());
            paciente.setRaca(pacienteAtualizado.getRaca());
            paciente.setDescricao(pacienteAtualizado.getDescricao());

            // Salva o paciente atualizado
            pacienteRepository.save(paciente);

            // Retorna o DTO após salvar
            return PacienteMapper.toDTO(paciente);
        }
        return null;  // Retorna null ou você pode lançar uma exceção caso o paciente não exista
    }

    // Remove um paciente do Tutor autenticado
    public void removerPaciente(String token, Long pacienteId) {
        String email = authService.getEmailFromToken();
        Tutor tutor = tutorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado"));

        Paciente paciente = pacienteRepository.findByIdAndTutor(pacienteId, tutor)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado ou não pertence ao tutor"));

        pacienteRepository.delete(paciente);
    }
}

