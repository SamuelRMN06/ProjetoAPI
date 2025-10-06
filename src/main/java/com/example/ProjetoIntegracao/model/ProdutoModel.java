package com.example.ProjetoIntegracao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private String descricao;
    private Double preco;
    private String marca;
    private Double peso;
    private Integer quantidade;


    @ManyToOne
    @JoinColumn(name = "loja_id")
    @JsonBackReference
    private LojaModel loja;
}
