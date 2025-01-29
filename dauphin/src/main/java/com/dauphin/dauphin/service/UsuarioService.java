package com.dauphin.dauphin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dauphin.dauphin.model.Usuario;
import com.dauphin.dauphin.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    // Salva um usuário no banco de dados
    public Usuario salvar(Usuario usuario) {
        usuario.printUsuario();

        try {
            return usuarioRepository.save(usuario);
        } 
        //Verifica a violação da integridade da base de dados.
        catch (DataIntegrityViolationException e){
            System.out.println("[DataIntegrityViolationException]:" + e.getMessage());
            throw new RuntimeException("Erro ao salvar o usuário. Verifique se o username ou email já estão em uso.");
        }
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        // Verifica se o username já está em uso
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("O nome de usuário já está em uso.");
        }

        // Verifica se o email já está em uso
        Optional<Usuario> emailExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (emailExistente.isPresent()) {
            throw new RuntimeException("O email já está em uso.");
        }

        // Salva o novo usuário no banco de dados
        return salvar(usuario);
    }
}
