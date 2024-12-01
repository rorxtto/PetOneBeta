package app.petone.repository;

import app.petone.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByVeterinario_Email(String email);
    @Query("SELECT a FROM Agendamento a WHERE a.paciente.tutor.email = :email")
    List<Agendamento> findByPacienteTutor_Email(@Param("email") String email);


    Optional<Agendamento> findByIdAndVeterinario_Email(Long id, String email);
}



