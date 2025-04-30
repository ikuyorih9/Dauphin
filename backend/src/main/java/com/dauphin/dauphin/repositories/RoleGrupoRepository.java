package com.dauphin.dauphin.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dauphin.dauphin.models.RoleGrupo;

@Repository
public interface RoleGrupoRepository extends JpaRepository<RoleGrupo, String>{
    public Optional<RoleGrupo> findByNome(String nome);
}
