package com.dauphin.dauphin.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dauphin.dauphin.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
    Optional<Usuario> findByUsername(String username);
    void deleteByUsername(String username);
}
