package app.petone.repository;

import app.petone.model.Paciente;
import app.petone.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findAllByTutor(Optional<Tutor> tutor); // Busca todos os pacientes de um tutor
    Optional<Paciente> findByIdAndTutor(Long id, Tutor tutor); // Busca paciente por ID e Tutor
}
