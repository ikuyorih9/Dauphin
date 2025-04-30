package com.dauphin.dauphin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dauphin.dauphin.models.Nivel;
import java.util.List;
import java.util.Optional;


@Repository
public interface NivelRepository extends JpaRepository<Nivel, String>{
    List<Nivel> findByClassificacao(String classificacao);
    
    @Query("SELECT n FROM Nivel n WHERE n.minimoPontos <= :pontuacao ORDER BY n.minimoPontos DESC LIMIT 1")
    Optional<Nivel> findClassificacaoByPontuacao(@Param("pontuacao") Integer pontuacao);
}
