package app.petone.DTO.v2.mapper;

import app.petone.DTO.v2.DTOs.AgendamentoDTO;
import app.petone.model.Agendamento;

public class AgendamentoMapper {

    public static AgendamentoDTO toDTO(Agendamento agendamento) {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamento.getId());
        dto.setDataAgendamento(agendamento.getDataAgendamento());
        dto.setAprovado(agendamento.getAprovado());
        dto.setDataCriacao(agendamento.getDataCriacao());
        dto.setDataAtualizacao(agendamento.getDataAtualizacao());
        dto.setStatus(agendamento.getStatus());
        dto.setDescricao(agendamento.getDescricao());
        // Mapear apenas IDs para evitar recursão
        if (agendamento.getPaciente() != null) {
            dto.setPacienteId(agendamento.getPaciente().getId());
        }
        if (agendamento.getVeterinario() != null) {
            dto.setVeterinarioId(agendamento.getVeterinario().getId());
        }
        if (agendamento.getProcedimento() != null) {
            dto.setProcedimentoId(agendamento.getProcedimento().getId());
        }

        return dto;
    }
}
