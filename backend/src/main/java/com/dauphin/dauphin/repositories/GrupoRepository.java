package com.dauphin.dauphin.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dauphin.dauphin.models.Grupo;
import com.dauphin.dauphin.models.Grupo.GrupoID;
import com.dauphin.dauphin.models.Usuario;

import java.util.List;


@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer>{
    public Optional<Grupo> findById(Integer id);

    @Query("SELECT g FROM Grupo g WHERE g.usuarioHost.username =:username ORDER BY g.id")
    public List<Grupo> findByHost(@Param("username") String username);
}
