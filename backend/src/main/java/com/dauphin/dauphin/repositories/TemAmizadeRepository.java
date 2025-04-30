package com.dauphin.dauphin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dauphin.dauphin.models.TemAmizade;
import com.dauphin.dauphin.models.TemAmizade.TemAmizadeId;

@Repository
public interface TemAmizadeRepository extends JpaRepository<TemAmizade, TemAmizadeId>{
    @Query("SELECT a FROM TemAmizade a WHERE a.id.username1 = :username ORDER BY a.id.username1 ASC")
    public List<TemAmizade> findByUsername(@Param("username") String username);
}
