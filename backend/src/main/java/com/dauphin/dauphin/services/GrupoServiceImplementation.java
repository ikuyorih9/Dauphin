package com.dauphin.dauphin.services;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

import com.dauphin.dauphin.dtos.GrupoCreateDTO;
import com.dauphin.dauphin.component.AuthService;
import com.dauphin.dauphin.component.JwtUtil;
import com.dauphin.dauphin.exceptions.EntityNotFoundInDatabaseException;
import com.dauphin.dauphin.exceptions.PermissionDeniedException;
import com.dauphin.dauphin.exceptions.TokenFailedException;
import com.dauphin.dauphin.models.Grupo;
import com.dauphin.dauphin.models.ParticipaGrupo;
import com.dauphin.dauphin.models.RoleGrupo;
import com.dauphin.dauphin.models.Usuario;
import com.dauphin.dauphin.repositories.GrupoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class GrupoServiceImplementation implements GrupoService{
    private GrupoRepository grupoRepository;
    private UsuarioService usuarioService;
    private JwtUtil jwtUtil;
    private AuthService authService;

    public GrupoServiceImplementation(GrupoRepository grupoRepository, UsuarioService usuarioService, JwtUtil jwtUtil, AuthService authService) {
        this.grupoRepository = grupoRepository;
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Grupo buscar(Integer id){
        return grupoRepository.findById(id).orElseThrow(() -> new EntityNotFoundInDatabaseException(EntityNotFoundInDatabaseException.GROUP_NOT_FOUND_MESSAGE));
    }

    @Override
    public Grupo criar(GrupoCreateDTO dto) {
        Usuario host = usuarioService.buscar(dto.getUsername());
        Grupo grupo = new Grupo(dto.getId(), host, LocalDate.now(), dto.getNome(), dto.getQtdMaxima(), dto.getVisibilidade());
        return grupoRepository.save(grupo);
    }

    @Override
    public void apagar(Integer id){
        Grupo toDelete = grupoRepository.findById(id).orElseThrow(() -> new EntityNotFoundInDatabaseException(EntityNotFoundInDatabaseException.GROUP_NOT_FOUND_MESSAGE));
        grupoRepository.delete(toDelete);
    }

    @Override
    public String gerarConvite(Integer id, Long expirationTime){
        // Verifica se o grupo existe.
        Grupo grupo = buscar(id);

        // tempo de expiração para o convite (ex: 1 dia)
        if(expirationTime == null){
            expirationTime = EXPIRATION_TIME;
        }
        try {
            return jwtUtil.generateToken((long) id, expirationTime.longValue());
        } 
        catch (Exception e) {
            throw new TokenFailedException(e.getMessage());
        }
    }

    @Override
    public long aceitarConvite(String token){
        try {
            return jwtUtil.extractGroupIdFromToken(token);
        } 
        catch (Exception e) {
            throw new TokenFailedException(e.getMessage());
        }
        
    }

    

}
