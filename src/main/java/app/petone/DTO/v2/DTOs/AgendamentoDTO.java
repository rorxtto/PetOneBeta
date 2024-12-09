package app.petone.DTO.v2.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoDTO {

    private Long id;
    private LocalDateTime dataAgendamento;
    private String descricao;
    private Long pacienteId;  // Apenas o ID do paciente
    private Long veterinarioId;  // Apenas o ID do veterinário
    private Long procedimentoId;  // Apenas o ID do procedimento
    private String status;
    private boolean aprovado;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}