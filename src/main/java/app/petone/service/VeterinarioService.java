package app.petone.service;

import app.petone.DTO.v2.DTOs.VeterinarioDTO;
import app.petone.auth.service.AuthService;
import app.petone.model.Veterinario;
import app.petone.repository.VeterinarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

@Service
public class VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private AuthService authService; // Para validar e extrair informações do JWT

    @Autowired
    private ModelMapper modelMapper; // Instância do ModelMapper

    // Lista todos os veterinários
    public List<VeterinarioDTO> listarTodos() {
        List<Veterinario> veterinarios = veterinarioRepository.findAll();
        return veterinarios.stream()
                .map(veterinario -> modelMapper.map(veterinario, VeterinarioDTO.class))
                .collect(Collectors.toList());
    }

    // Busca um veterinário pelo ID
    public VeterinarioDTO buscarPorId(Long id) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado"));
        return modelMapper.map(veterinario, VeterinarioDTO.class);
    }

    // Cria um novo veterinário
    public VeterinarioDTO criarVeterinario(Veterinario veterinario) {
        if (veterinarioRepository.existsByCpf(veterinario.getCpf())) {
            throw new IllegalArgumentException("Já existe um veterinário com este CPF");
        }
        veterinario.setDataCadastro(LocalDateTime.now());
        veterinario.setDataAtualizacao(LocalDateTime.now());
        Veterinario novoVeterinario = veterinarioRepository.save(veterinario);
        return modelMapper.map(novoVeterinario, VeterinarioDTO.class);
    }

    // Atualiza os dados de um veterinário
    public VeterinarioDTO atualizarVeterinario(Long id, Veterinario dadosAtualizados) {
        Veterinario veterinario = veterinarioRepository.findById(id)  // Recupera o veterinário pelo ID
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado"));  // Lança exceção se não encontrar

        // Atualiza os dados do veterinário
        veterinario.setNome(dadosAtualizados.getNome());
        veterinario.setCpf(dadosAtualizados.getCpf());
        veterinario.setCrmv(dadosAtualizados.getCrmv());
        veterinario.setCep(dadosAtualizados.getCep());
        veterinario.setRua(dadosAtualizados.getRua());
        veterinario.setNumero(dadosAtualizados.getNumero());
        veterinario.setBairro(dadosAtualizados.getBairro());
        veterinario.setCidade(dadosAtualizados.getCidade());
        veterinario.setEmail(dadosAtualizados.getEmail());
        veterinario.setEstado(dadosAtualizados.getEstado());
        veterinario.setDataAtualizacao(LocalDateTime.now());

        // Salva as alterações no banco
        this.veterinarioRepository.save(veterinario);
        return buscarPorId(veterinario.getId());
    }


    // Remove um veterinário pelo ID
    public void removerVeterinario(Long id) {
        veterinarioRepository.deleteById(id);
    }

    // Busca o veterinário logado (usando o email do token JWT)
    public VeterinarioDTO buscarVeterinarioLogado(String email) {
        Veterinario veterinario = veterinarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado"));
        return modelMapper.map(veterinario, VeterinarioDTO.class);
    }
}