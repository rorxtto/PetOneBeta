package app.petone.DTO;

import app.petone.model.Tutor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TutorDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private List<PacienteDTO> pacientes;

    public TutorDTO(Tutor tutor) {
        this.id = tutor.getId();
        this.username = tutor.getUsername();
        this.email = tutor.getEmail();
        this.firstName = tutor.getFirstName();
        this.lastName = tutor.getLastName();
        this.role = tutor.getRole();
        this.pacientes = tutor.getPacientes().stream()
                .map(PacienteDTO::new)
                .toList();
    }
}

