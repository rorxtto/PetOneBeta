package app.petone.DTO.v2.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VeterinarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String crmv;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private String rua;
    private String bairro;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;
}
