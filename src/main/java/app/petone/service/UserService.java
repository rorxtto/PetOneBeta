package app.petone.service;

import app.petone.auth.model.TokenDTO;
import app.petone.auth.service.AuthService;
import app.petone.model.Tutor;
import app.petone.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private TutorRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private LogService logService;

    public Optional<Tutor> getUserByEmail(String email) {
        if (email != null && !email.isEmpty()) {
            return userRepository.findByEmail(email);
        }
        return Optional.of(new Tutor());
    }

    public void loadUser(TokenDTO auxtoken) {
        if (auxtoken.getEmail() != null && !auxtoken.getEmail().isEmpty()) {
            Optional<Tutor> existingUser = getUserByEmail(auxtoken.getEmail());

            if (existingUser.isPresent()) {
                return; // Usuário já existe
            }

            // Criação do novo usuário
            Tutor newUser = new Tutor();
            newUser.setEmail(auxtoken.getEmail());
            newUser.setUsername(auxtoken.getEmail());
            newUser.setFirstName(auxtoken.getGivenName());
            newUser.setLastName(auxtoken.getFamilyName());
            newUser.setRole("USER");
            this.logService.Created("user", newUser.getUsername(), authService.getEmailFromToken());
            this.userRepository.save(newUser); // Salvar no banco
            System.out.println("Novo usuario: " + auxtoken.getEmail());
        }
    }


}
