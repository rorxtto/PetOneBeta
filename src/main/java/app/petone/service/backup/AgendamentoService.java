/*

package app.petone.service.backup;

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

    // Lista todos os agendamentos associados ao veterinário logado
    public List<Agendamento> listarPorVeterinario(String emailVeterinario) {
        return agendamentoRepository.findByVeterinario_Email(emailVeterinario);
    }

    public List<Agendamento> listarPorTutor(String emailVeterinario) {
        return agendamentoRepository.findByPacienteTutor_Email(emailVeterinario);
    }

    // Busca um agendamento por ID
    public Agendamento buscarPorId(Long id, String emailVeterinario) {
        return agendamentoRepository.findByIdAndVeterinario_Email(id, emailVeterinario)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado ou não autorizado"));
    }

    // Cria um novo agendamento
    public Agendamento criarAgendamento(Agendamento agendamento, String emailVeterinario, Long id, Long pId) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado"));
        /* Integração futura para o veterinario criar agendamento

            Veterinario veterinario = veterinarioRepository.findByEmail(emailVeterinario)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado"));

                Procedimento procedimento = procedimentoRepository.findById(agendamento.getProcedimento().getId())
                .orElseThrow(() -> new EntityNotFoundException("Procedimento não encontrado"));



import app.petone.model.Agendamento;
import app.petone.model.Paciente;
import app.petone.model.Procedimento;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

Procedimento procedimento = procedimentoRepository.findById(pId)
                .orElseThrow(() -> new EntityNotFoundException("Procedimento não encontrado"));

        Paciente paciente = pacienteRepository.findById(agendamento.getPaciente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        agendamento.setVeterinario(veterinario);
        agendamento.setProcedimento(procedimento);
        agendamento.setPaciente(paciente);
        agendamento.setAprovado(false); // Agendamento começa não aprovado
        agendamento.setDataCriacao(LocalDateTime.now());
        agendamento.setDataAtualizacao(LocalDateTime.now());

        return agendamentoRepository.save(agendamento);
    }

    public Agendamento aprovarAgendamento(Long id, boolean aprovado, String emailVeterinario) {
        Agendamento agendamento = buscarPorId(id, emailVeterinario);
        agendamento.setAprovado(aprovado);
        agendamento.setDataAtualizacao(LocalDateTime.now());
        return agendamentoRepository.save(agendamento);
    }

    public void removerAgendamento(Long id, String emailVeterinario) {
        Agendamento agendamento = buscarPorId(id, emailVeterinario);
        agendamentoRepository.delete(agendamento);
    }
}

*/