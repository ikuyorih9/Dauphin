package com.dauphin.dauphin.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dauphin.dauphin.models.ParticipaGrupo;
import com.dauphin.dauphin.models.RoleGrupo;
import com.dauphin.dauphin.models.ParticipaGrupo.ParticipaGrupoId;

@Repository
public interface ParticipaGrupoRepository extends JpaRepository<ParticipaGrupo, ParticipaGrupoId>{
    public Optional<ParticipaGrupo> findById(ParticipaGrupoId id);

    @Query("SELECT p.role FROM ParticipaGrupo p WHERE p.id = :id")
    public Optional<RoleGrupo> findRoleById(@Param ("id") ParticipaGrupoId id);

    @Query("SELECT p FROM ParticipaGrupo p WHERE p.id.id = :id")
    public List<ParticipaGrupo> findByGroup(@Param("id") Integer id);
}
