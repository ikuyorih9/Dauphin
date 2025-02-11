package com.dauphin.dauphin.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dauphin.dauphin.model.Usuario;

public interface UsuarioService {
    public List listar();
    public Usuario cadastrar(Usuario usuario);
    public Usuario autenticar(String username, String senha);
    public boolean jaCadastrado(Usuario usuario);
    public Usuario busca(String username);
    public List listarAmigos(String username);
}
