package com.dauphin.dauphin.repository;

import com.dauphin.dauphin.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
}
