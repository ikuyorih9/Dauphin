package com.dauphin.dauphin.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dauphin.dauphin.model.Grupo;
import com.dauphin.dauphin.model.Usuario;
import com.dauphin.dauphin.repository.GrupoRepository;

@Service
public class GrupoServiceImplementation implements GrupoService{
    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Grupo criarGrupo(String username, Grupo grupo) {
        Usuario userHost = usuarioService.busca(username);
        grupo.setUsuarioHost(userHost);
        grupo.setDataCriacao(LocalDate.now());
        // if(grupo.getQtdMaxima() == null)
        //     grupo.setQtdMaxima(20);

        System.out.println("[GRUPO]: " + grupo.toString());

        return grupoRepository.save(grupo);
    }
}
