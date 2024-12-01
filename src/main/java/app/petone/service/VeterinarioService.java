package app.petone.service;

import app.petone.auth.service.AuthService;
import app.petone.model.Veterinario;
import app.petone.repository.VeterinarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private AuthService authService; // Para validar e extrair informações do JWT

    // Lista todos os veterinários
    public List<Veterinario> listarTodos() {
        return veterinarioRepository.findAll();
    }

    // Busca um veterinário pelo ID
    public Veterinario buscarPorId(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado"));
    }

    // Cria um novo veterinário
    public Veterinario criarVeterinario(Veterinario veterinario) {
        if (veterinarioRepository.existsByCpf(veterinario.getCpf())) {
            throw new IllegalArgumentException("Já existe um veterinário com este CPF");
        }
        veterinario.setDataCadastro(LocalDateTime.now());
        veterinario.setDataAtualizacao(LocalDateTime.now());
        return veterinarioRepository.save(veterinario);
    }

    // Atualiza os dados de um veterinário
    public Veterinario atualizarVeterinario(Long id, Veterinario dadosAtualizados) {
        Veterinario veterinario = buscarPorId(id);
        veterinario.setNome(dadosAtualizados.getNome());
        veterinario.setCpf(dadosAtualizados.getCpf());
        veterinario.setCrm(dadosAtualizados.getCrm());
        veterinario.setCep(dadosAtualizados.getCep());
        veterinario.setRua(dadosAtualizados.getRua());
        veterinario.setNumero(dadosAtualizados.getNumero());
        veterinario.setBairro(dadosAtualizados.getBairro());
        veterinario.setCidade(dadosAtualizados.getCidade());
        veterinario.setEmail(dadosAtualizados.getEmail());
        veterinario.setEstado(dadosAtualizados.getEstado());
        veterinario.setDataAtualizacao(LocalDateTime.now());
        return veterinarioRepository.save(veterinario);
    }

    // Remove um veterinário pelo ID
    public void removerVeterinario(Long id) {
        Veterinario veterinario = buscarPorId(id);
        veterinarioRepository.delete(veterinario);
    }

    // Busca o veterinário logado (usando o email do token JWT)
    public Veterinario buscarVeterinarioLogado(String email) {
        return veterinarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado"));
    }
}
