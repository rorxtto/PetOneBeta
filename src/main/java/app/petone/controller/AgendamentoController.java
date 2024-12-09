package app.petone.controller;

import app.petone.DTO.v2.DTOs.AgendamentoDTO;
import app.petone.model.Agendamento;
import app.petone.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping("/{id}")
    public AgendamentoDTO buscarPorId(@PathVariable Long id, @RequestHeader String emailVeterinario) {
        return agendamentoService.buscarPorId(id, emailVeterinario);
    }

    @GetMapping("/veterinario")
    public List<AgendamentoDTO> listarPorVeterinario(@RequestHeader String emailVeterinario) {
        return agendamentoService.listarPorVeterinario(emailVeterinario);
    }


    @GetMapping("/tutor")
    public List<AgendamentoDTO> listarPorTutor() {
        return agendamentoService.listarPorTutor();
    }

    @PostMapping("/{veterinarioId}/{procedimentoId}/{pacienteId}")
    public AgendamentoDTO criarAgendamento(
            @RequestBody Agendamento agendamento,
            @PathVariable Long veterinarioId,
            @PathVariable Long procedimentoId,
            @PathVariable Long pacienteId) {
        System.out.println(agendamento.getDescricao());
        return agendamentoService.criarAgendamento(agendamento, veterinarioId, procedimentoId, pacienteId);
    }


    @PutMapping("/{id}")
    public AgendamentoDTO aprovarAgendamento(@PathVariable Long id, @RequestParam boolean aprovado,
                                             @RequestHeader String emailVeterinario) {
        return agendamentoService.aprovarAgendamento(id, aprovado, emailVeterinario);
    }

    @DeleteMapping("/{id}")
    public void removerAgendamento(@PathVariable Long id) {
        agendamentoService.removerAgendamento(id);
    }
}
