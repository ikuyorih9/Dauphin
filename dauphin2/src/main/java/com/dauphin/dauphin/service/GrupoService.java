package com.dauphin.dauphin.service;

import java.util.List;

import com.dauphin.dauphin.id.GrupoId;
import com.dauphin.dauphin.model.Grupo;
import com.dauphin.dauphin.model.Usuario;

public interface GrupoService {
    public Grupo criar(String username, Grupo grupo);
    public List <Grupo> listar(String username);
    public void apagar(GrupoId id);
}
