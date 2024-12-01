package app.petone.service;

import app.petone.model.Procedimento;
import app.petone.repository.ProcedimentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProcedimentoService {
    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    // Lista todos os procedimentos
    public List<Procedimento> listarTodos() {
        return procedimentoRepository.findAll();
    }

    // Busca um procedimento por ID
    public Procedimento buscarPorId(Long id) {
        return procedimentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Procedimento não encontrado"));
    }

    // Cria um novo procedimento
    public Procedimento criarProcedimento(Procedimento procedimento) {
        procedimento.setDataCriacao(LocalDateTime.now());
        procedimento.setDataAtualizacao(LocalDateTime.now());
        return procedimentoRepository.save(procedimento);
    }

    // Atualiza um procedimento existente
    public Procedimento atualizarProcedimento(Long id, Procedimento procedimentoAtualizado) {
        Procedimento procedimento = buscarPorId(id);

        procedimento.setNome(procedimentoAtualizado.getNome());
        procedimento.setDataAtualizacao(LocalDateTime.now());

        return procedimentoRepository.save(procedimento);
    }

    // Remove um procedimento
    public void removerProcedimento(Long id) {
        Procedimento procedimento = buscarPorId(id);
        procedimentoRepository.delete(procedimento);
    }
}
