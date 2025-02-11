package com.dauphin.dauphin.repository;

import com.dauphin.dauphin.id.GrupoId;
import com.dauphin.dauphin.model.Grupo;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, GrupoId>{

}
