package com.dauphin.dauphin.repository;

import org.springframework.stereotype.Repository;

import com.dauphin.dauphin.model.TemAmizade;
import com.dauphin.dauphin.id.TemAmizadeId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface TemAmizadeRepository extends JpaRepository<TemAmizade, TemAmizadeId>{
    @Query(
        "SELECT t FROM TemAmizade t WHERE " +
        "(t.usuario1.username = :username1 AND t.usuario2.username = :username2) OR " +
        "(t.usuario1.username = :username2 AND t.usuario2.username = :username1)")
    TemAmizade temAmizade(@Param("username1") String username1, @Param("username2") String username2);

    @Query("SELECT a FROM TemAmizade a WHERE a.usuario1.username = :username OR a.usuario2.username = :username")
    List<TemAmizade> buscarAmizades(@Param("username") String username);
}

