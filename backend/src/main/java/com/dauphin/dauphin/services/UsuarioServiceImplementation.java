package com.dauphin.dauphin.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dauphin.dauphin.component.AuthService;
import com.dauphin.dauphin.dtos.UsuarioCadastroDTO;
import com.dauphin.dauphin.exceptions.EntityConflictInDatabaseException;
import com.dauphin.dauphin.exceptions.EntityNotFoundInDatabaseException;
import com.dauphin.dauphin.exceptions.PermissionDeniedException;
import com.dauphin.dauphin.models.Nivel;
import com.dauphin.dauphin.models.Usuario;
import com.dauphin.dauphin.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImplementation implements UsuarioService{
    private UsuarioRepository usuarioRepository;
    private NivelService nivelService;
    private PasswordEncoder passwordEncoder;
    private AuthService authService;

    public UsuarioServiceImplementation(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, NivelService nivelService, AuthService authService){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.nivelService = nivelService;
        this.authService = authService;
    }

    @Override
    public List<Usuario> lista() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario cadastrar(UsuarioCadastroDTO dto){
        LocalDate dataCadastro = LocalDate.now();
        Integer pontuacao = 0;
        String role = "USER";
        Nivel nivel = nivelService.obtemPontuacao(pontuacao);

        if(usuarioRepository.findByUsername(dto.getUsername()).isPresent())
            throw new EntityConflictInDatabaseException("Usuário " + dto.getUsername() + " já está cadastrado no sistema!");

        Usuario usuario = new Usuario(
            dto.getUsername(), 
            dto.getEmail(),
            dto.getNome(),
            passwordEncoder.encode(dto.getSenha()),
            dto.getSexo(),
            dto.getDataNascimento(),
            dataCadastro,
            dto.getFoto(),
            pontuacao,
            role,
            nivel);
        
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscar(String username){
        return usuarioRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundInDatabaseException("Usuário " + username + " não foi encontrado na base de dados!"));
    }

    @Override
    public void deletar(String username) {
        String currentUser = authService.getCurrentUsername();
        if(! username.equals(currentUser)) throw new PermissionDeniedException();

        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundInDatabaseException());
        
        // Remove o usuário da base de dados
        usuarioRepository.deleteByUsername(username);
    }

}
