package com.dauphin.dauphin.services;

import java.util.List;

import com.dauphin.dauphin.dtos.GroupNewUserDTO;
import com.dauphin.dauphin.interfaces.CRUD;
import com.dauphin.dauphin.interfaces.ID;
import com.dauphin.dauphin.models.ParticipaGrupo;
import com.dauphin.dauphin.models.ParticipaGrupo.ParticipaGrupoId;
import com.dauphin.dauphin.models.RoleGrupo;

public interface ParticipaGrupoService extends CRUD<ParticipaGrupo, ParticipaGrupoId, GroupNewUserDTO>{
    public RoleGrupo buscaRole(ParticipaGrupoId id);
    public boolean membroTemPermissao(ParticipaGrupoId id, String permissao);
    public void alterarRole(Integer id, String username, String newRoleName);
    public List<ParticipaGrupo> buscaParticipantes(Integer id);
}
