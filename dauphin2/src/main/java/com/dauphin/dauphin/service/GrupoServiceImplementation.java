package com.dauphin.dauphin.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dauphin.dauphin.exception.UserNotFoundException;
import com.dauphin.dauphin.id.GrupoId;
import com.dauphin.dauphin.model.Grupo;
import com.dauphin.dauphin.model.Usuario;
import com.dauphin.dauphin.repository.GrupoRepository;

@Service
public class GrupoServiceImplementation implements GrupoService{
    private GrupoRepository grupoRepository;
    private UsuarioService usuarioService;

    public GrupoServiceImplementation(GrupoRepository grupoRepository, UsuarioService usuarioService){
        this.grupoRepository = grupoRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public Grupo criar(String username, Grupo grupo) {
        Usuario userHost = usuarioService.busca(username);
        grupo.setUsuarioHost(userHost);
        grupo.setDataCriacao(LocalDate.now());

        System.out.println("[GRUPO]: " + grupo.toString());

        return grupoRepository.save(grupo);
    }

    @Override
    public List <Grupo> listar(String username){
        return grupoRepository.findByUsuarioHost_Username(username);
    }

    @Override
    public void apagar(GrupoId id){
        grupoRepository.deleteById(id);
    }
}
