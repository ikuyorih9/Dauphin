package com.dauphin.dauphin.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dauphin.dauphin.exception.UniqueKeyException;
import com.dauphin.dauphin.exception.UserNotFoundException;
import com.dauphin.dauphin.model.Nivel;
import com.dauphin.dauphin.model.Usuario;
import com.dauphin.dauphin.repository.NivelRepository;
import com.dauphin.dauphin.repository.TemAmizadeRepository;
import com.dauphin.dauphin.repository.UsuarioRepository;

@Service
public class UsuarioServiceImplement implements UsuarioService{
    UsuarioRepository usuarioRepository;
    TemAmizadeService temAmizadeService;
    NivelService nivelService;

    public UsuarioServiceImplement(UsuarioRepository usuarioRepository, NivelService nivelService, TemAmizadeService temAmizadeService){
        this.usuarioRepository = usuarioRepository;
        this.nivelService = nivelService;
        this.temAmizadeService = temAmizadeService;
    }

    @Override
    public List listar(){
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario cadastrar(Usuario usuario) {
        if (jaCadastrado(usuario))
            throw new UniqueKeyException("Nome de usuário ou email já estão em uso.");

        usuario.setDataCadastro(LocalDate.now());

        if (usuario.getPontuacao() == null)
            usuario.setPontuacao(0);
        
        usuario.setNivel(nivelService.determinaNivel(usuario.getPontuacao()));
        
        // Salva o novo usuário no banco de dados
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario autenticar(String username, String senha) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsername(username);

        if (usuarioExistente.isEmpty()) {
            throw new UserNotFoundException("Usuário não encontrado!");
        }

        Usuario usuario = usuarioExistente.get();

        // Verifica a senha criptografada
        if (!senha.equals(usuario.getSenha())) {
            return null;
        }

        return usuario; // Retorna o usuário autenticado
    }

    @Override
    public boolean jaCadastrado(Usuario usuario){
        // Verifica se o username já está em uso
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioExistente.isPresent()) 
            return true;

        // Verifica se o email já está em uso
        Optional<Usuario> emailExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (emailExistente.isPresent()) 
            return true;
        return false;
    }

    @Override
    public Usuario busca(String username){
        return usuarioRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Usuário " + username + " não está cadastrado."));
    }

    @Override
    public List listarAmigos(String username){
        return temAmizadeService.listar(busca(username).getUsername());
    }
}
