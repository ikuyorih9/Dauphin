package com.dauphin.dauphin.repository;

import com.dauphin.dauphin.id.GrupoId;
import com.dauphin.dauphin.model.Grupo;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, GrupoId>{
    List<Grupo> findByUsuarioHost_Username(String usuarioHost);
}
