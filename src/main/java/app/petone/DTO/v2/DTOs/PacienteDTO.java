package app.petone.DTO.v2.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PacienteDTO {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String especie;
    private String raca;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Long tutorId;

}