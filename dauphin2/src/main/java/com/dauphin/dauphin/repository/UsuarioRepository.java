package com.dauphin.dauphin.repository;

import com.dauphin.dauphin.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
}
