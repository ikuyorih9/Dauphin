package com.dauphin.dauphin.services;

import java.util.List;

import com.dauphin.dauphin.models.RoleGrupo;

public interface RoleGrupoService {
    public RoleGrupo buscar(String nome);
    public List<RoleGrupo> buscaTodos();
    public boolean podeConvidar(String nome);
    public boolean podeRemover(String nome);
    public boolean podeEditar(String nome);
}
