package com.example.ProjetoIntegracao.controller;

import com.example.ProjetoIntegracao.model.LojaModel;
import com.example.ProjetoIntegracao.model.ProdutoModel;
import com.example.ProjetoIntegracao.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoModel>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoModel> criarProduto(@RequestBody ProdutoModel produto) {
        ProdutoModel novoProduto = produtoService.criarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModel> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoModel produto) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, produto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoModel> atualizarParcialLoja(@PathVariable Long id, @RequestBody ProdutoModel produtoAtualizado) {
        ProdutoModel produtoAtual = produtoService.atualizarParcialProduto(id, produtoAtualizado);
        return ResponseEntity.ok(produtoAtual);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }
}