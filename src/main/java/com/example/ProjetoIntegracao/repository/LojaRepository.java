package com.example.ProjetoIntegracao.repository;

import com.example.ProjetoIntegracao.model.LojaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LojaRepository extends JpaRepository<LojaModel, Long> {

}