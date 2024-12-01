package app.petone.DTO.v2.mapper;

import app.petone.DTO.v2.DTOs.PacienteDTO;
import app.petone.model.Paciente;

public class PacienteMapper {

    public static PacienteDTO toDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setEspecie(paciente.getEspecie());
        dto.setRaca(paciente.getRaca());
        dto.setDescricao(paciente.getDescricao());
        dto.setDataCriacao(paciente.getDataCriacao());
        dto.setDataAtualizacao(paciente.getDataAtualizacao());

        // Evitar recursão, apenas incluímos o ID do tutor
        if (paciente.getTutor() != null) {
            dto.setTutorId(paciente.getTutor().getId());
        }

        return dto;
    }
}
