package com.example.ProjetoIntegracao.controller;

import com.example.ProjetoIntegracao.model.LojaModel;
import com.example.ProjetoIntegracao.service.LojaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lojas")
public class LojaController {

   private final LojaService lojaService;

   public LojaController(LojaService lojaService) {
      this.lojaService = lojaService;
   }

   @GetMapping
   public ResponseEntity<List<LojaModel>> listarLojas() {
      return ResponseEntity.ok(lojaService.listarLojas());
   }

   @GetMapping("/{id}")
   public ResponseEntity<LojaModel> buscarPorId(@PathVariable Long id) {
      return ResponseEntity.ok(lojaService.buscarPorId(id));
   }

   @PostMapping
   public ResponseEntity<LojaModel> criarLoja(@RequestBody LojaModel loja) {
      LojaModel novaLoja = lojaService.criarLoja(loja);
      return ResponseEntity.status(HttpStatus.CREATED).body(novaLoja);
   }

   @PutMapping("/{id}")
   public ResponseEntity<LojaModel> atualizarLoja(@PathVariable Long id, @RequestBody LojaModel loja) {
      return ResponseEntity.ok(lojaService.atualizarLoja(id, loja));
   }

   @PatchMapping("/{id}")
   public ResponseEntity<LojaModel> atualizarParcialLoja(@PathVariable Long id, @RequestBody LojaModel lojaAtualizada) {
      LojaModel lojaAtual = lojaService.atualizarParcialLoja(id, lojaAtualizada);
      return ResponseEntity.ok(lojaAtual);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> excluirLoja(@PathVariable Long id) {
      lojaService.excluirLoja(id);
      return ResponseEntity.noContent().build();
   }
}