package app.petone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String firstName;
    private String lastName;
    private String role;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    private List<Paciente> pacientes;
}