package com.example.ProjetoIntegracao.service;

import com.example.ProjetoIntegracao.model.LojaModel;
import com.example.ProjetoIntegracao.repository.LojaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LojaService {

    private final LojaRepository lojaRepository;

    public LojaService(LojaRepository lojaRepository) {
        this.lojaRepository = lojaRepository;
    }

    public List<LojaModel> listarLojas() {
        return lojaRepository.findAll();
    }

    public LojaModel buscarPorId(Long id) {
        return lojaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loja com ID " + id + " não encontrada"));
    }

    public LojaModel criarLoja(LojaModel loja) {
        if (loja.getNome() == null || loja.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'nome' é obrigatório");
        }

        try {
            return lojaRepository.save(loja);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar loja: " + e.getMessage());
        }
    }

    public LojaModel atualizarLoja(Long id, LojaModel lojaAtualizada) {
        LojaModel lojaExistente = buscarPorId(id);

        if (lojaAtualizada.getNome() == null || lojaAtualizada.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'nome' é obrigatório");
        }

        try {
            lojaExistente.setNome(lojaAtualizada.getNome());
            lojaExistente.setEndereco(lojaAtualizada.getEndereco());
            lojaExistente.setTelefone(lojaAtualizada.getTelefone());
            lojaExistente.setEmail(lojaAtualizada.getEmail());
            lojaExistente.setDescricao(lojaAtualizada.getDescricao());
            lojaExistente.setCnpj(lojaAtualizada.getCnpj());

            return lojaRepository.save(lojaExistente);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar loja: " + e.getMessage());
        }
    }

    public LojaModel atualizarParcialLoja(Long id, LojaModel lojaAtualizada) {
        LojaModel lojaExistente = buscarPorId(id);

        if (lojaAtualizada.getNome() != null) {
            lojaExistente.setNome(lojaAtualizada.getNome());
        }
        if (lojaAtualizada.getEndereco() != null) {
            lojaExistente.setEndereco(lojaAtualizada.getEndereco());
        }
        if (lojaAtualizada.getTelefone() != null) {
            lojaExistente.setTelefone(lojaAtualizada.getTelefone());
        }
        if (lojaAtualizada.getEmail() != null) {
            lojaExistente.setEmail(lojaAtualizada.getEmail());
        }
        if (lojaAtualizada.getDescricao() != null) {
            lojaExistente.setDescricao(lojaAtualizada.getDescricao());
        }
        if (lojaAtualizada.getCnpj() != null) {
            lojaExistente.setCnpj(lojaAtualizada.getCnpj());
        }

        try {
            return lojaRepository.save(lojaExistente);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar parcialmente loja: " + e.getMessage());
        }
    }

    public void excluirLoja(Long id) {
        LojaModel loja = buscarPorId(id);
        try {
            lojaRepository.delete(loja);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao excluir loja: " + e.getMessage());
        }
    }
}