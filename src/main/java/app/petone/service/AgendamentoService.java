package app.petone.service;

import app.petone.DTO.v2.DTOs.AgendamentoDTO;
import app.petone.DTO.v2.mapper.AgendamentoMapper;
import app.petone.auth.service.AuthService;
import app.petone.model.Agendamento;
import app.petone.model.Paciente;
import app.petone.model.Procedimento;
import app.petone.model.Veterinario;
import app.petone.repository.AgendamentoRepository;
import app.petone.repository.PacienteRepository;
import app.petone.repository.ProcedimentoRepository;
import app.petone.repository.VeterinarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private AuthService authService;

    // Método privado para buscar a entidade Agendamento sem retornar DTO
    private Agendamento buscarAgendamentoPorId(Long id, String emailVeterinario) {
        return agendamentoRepository.findByIdAndVeterinario_Email(id, emailVeterinario)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado ou não autorizado"));
    }

    // Busca um agendamento por ID e retorna o DTO correspondente
    public AgendamentoDTO buscarPorId(Long id, String emailVeterinario) {
        Agendamento agendamento = buscarAgendamentoPorId(id, emailVeterinario);
        return AgendamentoMapper.toDTO(agendamento);
    }

    // Lista todos os agendamentos de um veterinário e retorna DTOs
    public List<AgendamentoDTO> listarPorVeterinario(String emailVeterinario) {
        List<Agendamento> agendamentos = agendamentoRepository.findByVeterinario_Email(emailVeterinario);
        return agendamentos.stream()
                .map(AgendamentoMapper::toDTO)
                .toList();
    }

    // Lista todos os agendamentos associados a um tutor e retorna DTOs
    public List<AgendamentoDTO> listarPorTutor() {
        String email = authService.getEmailFromToken();
        List<Agendamento> agendamentos = agendamentoRepository.findByPacienteTutor_Email(email);
        return agendamentos.stream()
                .map(AgendamentoMapper::toDTO)
                .toList();
    }

    public AgendamentoDTO criarAgendamento(Agendamento agendamento, Long veterinarioId, Long procedimentoId, Long pacienteId) {
        // Encontra o veterinário pelo ID
        Veterinario veterinario = veterinarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado"));

        // Encontra o procedimento pelo ID
        Procedimento procedimento = procedimentoRepository.findById(procedimentoId)
                .orElseThrow(() -> new EntityNotFoundException("Procedimento não encontrado"));

        // Encontra o paciente pelo ID do agendamento
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        // Define as informações do agendamento
        agendamento.setVeterinario(veterinario);
        agendamento.setProcedimento(procedimento);
        agendamento.setPaciente(paciente);
        agendamento.setStatus("AGUARDANDO");
        agendamento.setAprovado(false); // O agendamento começa como não aprovado
        agendamento.setDataCriacao(LocalDateTime.now());
        agendamento.setDataAtualizacao(LocalDateTime.now());

        // Salva o agendamento no repositório
        Agendamento savedAgendamento = agendamentoRepository.save(agendamento);
        System.out.println(savedAgendamento.getStatus());
        // Retorna o DTO do agendamento salvo
        return AgendamentoMapper.toDTO(savedAgendamento);
    }



    // Atualiza o status de aprovação do agendamento e retorna o DTO correspondente
    public AgendamentoDTO aprovarAgendamento(Long id, boolean aprovado, String emailVeterinario) {
        Agendamento agendamento = buscarAgendamentoPorId(id, emailVeterinario);
        agendamento.setAprovado(aprovado);
        agendamento.setDataAtualizacao(LocalDateTime.now());
        Agendamento updatedAgendamento = agendamentoRepository.save(agendamento);
        return AgendamentoMapper.toDTO(updatedAgendamento);
    }

    // Remove um agendamento
    public void removerAgendamento(Long id, String emailVeterinario) {
        Agendamento agendamento = buscarAgendamentoPorId(id, emailVeterinario);
        agendamentoRepository.delete(agendamento);
    }
}
