package com.dauphin.dauphin.services;

import java.util.List;

import com.dauphin.dauphin.dtos.UsuarioCadastroDTO;
import com.dauphin.dauphin.models.Usuario;

public interface UsuarioService {
    public List<Usuario> lista();
    public Usuario cadastrar(UsuarioCadastroDTO dto);
    public Usuario buscar(String username);
}
