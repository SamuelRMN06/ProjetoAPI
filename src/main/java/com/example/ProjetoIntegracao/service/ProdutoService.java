package com.example.ProjetoIntegracao.service;

import com.example.ProjetoIntegracao.model.ProdutoModel;
import com.example.ProjetoIntegracao.model.LojaModel;
import com.example.ProjetoIntegracao.repository.ProdutoRepository;
import com.example.ProjetoIntegracao.repository.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final LojaRepository lojaRepository;

    private final ProdutoRepository produtoRepository;


    public List<ProdutoModel> listarProdutos() {
        return produtoRepository.findAll();
    }

    public ProdutoModel buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto com ID " + id + " não encontrado"));
    }

    public ProdutoModel criarProduto(ProdutoModel produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'nome' é obrigatório");
        }
        try {
            Long lojaId = produto.getLoja().getId();
            LojaModel loja = lojaRepository.findById(lojaId)
                    .orElseThrow(() -> new RuntimeException("Loja não encontrada"));
            produto.setLoja(loja);
            return produtoRepository.save(produto);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar produto: " + e.getMessage());
        }
    }

    public ProdutoModel atualizarProduto(Long id, ProdutoModel produtoAtualizado) {
        ProdutoModel produto = buscarPorId(id);

        if (produtoAtualizado.getNome() == null || produtoAtualizado.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'nome' é obrigatório");
        }
        try {
            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setMarca(produtoAtualizado.getMarca());
            produto.setPeso(produtoAtualizado.getPeso());
            produto.setQuantidade(produtoAtualizado.getQuantidade());
            produto.setLoja(produtoAtualizado.getLoja());
            return produtoRepository.save(produto);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public ProdutoModel atualizarParcialProduto(Long id, ProdutoModel produtoAtualizado) {
        ProdutoModel produtoExistente = buscarPorId(id);

        if (produtoAtualizado.getNome() != null) {
            produtoExistente.setNome(produtoAtualizado.getNome());
        }
        if (produtoAtualizado.getDescricao() != null) {
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        }
        if (produtoAtualizado.getPreco() != null) {
            produtoExistente.setPreco(produtoAtualizado.getPreco());
        }
        if (produtoAtualizado.getMarca() != null) {
            produtoExistente.setMarca(produtoAtualizado.getMarca());
        }
        if (produtoAtualizado.getPeso() != null) {
            produtoExistente.setPeso(produtoAtualizado.getPeso());
        }
        if (produtoAtualizado.getQuantidade() != null) {
            produtoExistente.setQuantidade(produtoAtualizado.getQuantidade());
        }
        if (produtoAtualizado.getLoja() != null){
            produtoExistente.setLoja(produtoAtualizado.getLoja());
        }

        try {
            return produtoRepository.save(produtoExistente);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar parcialmente o produto: " + e.getMessage());
        }
    }

    public void excluirProduto(Long id) {
        ProdutoModel produto = buscarPorId(id);
        try {
            produtoRepository.delete(produto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao excluir produto: " + e.getMessage());
        }
    }
}