package app.petone.DTO;

import app.petone.model.Paciente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteDTO {
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String especie;
    private String raca;
    private String descricao;

    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.dataNascimento = paciente.getDataNascimento();
        this.especie = paciente.getEspecie();
        this.raca = paciente.getRaca();
        this.descricao = paciente.getDescricao();
    }
}

