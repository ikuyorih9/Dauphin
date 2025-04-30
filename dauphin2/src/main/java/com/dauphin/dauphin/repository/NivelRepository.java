package com.dauphin.dauphin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dauphin.dauphin.model.Nivel;

public interface NivelRepository extends JpaRepository<Nivel, String>{
    @Query("SELECT n FROM Nivel n WHERE n.minimoPontos <= :pontuacao ORDER BY n.minimoPontos DESC LIMIT 1")
    Optional<Nivel> determinaNivel(@Param("pontuacao") Integer pontuacao);
    // Optional<Nivel> findByClassificacao(String classificacao);
}
