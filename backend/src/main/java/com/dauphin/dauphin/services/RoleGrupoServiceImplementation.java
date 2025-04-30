package com.dauphin.dauphin.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dauphin.dauphin.models.RoleGrupo;
import com.dauphin.dauphin.repositories.RoleGrupoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleGrupoServiceImplementation implements RoleGrupoService{
    private RoleGrupoRepository roleGrupoRepository;

    public RoleGrupoServiceImplementation(RoleGrupoRepository roleGrupoRepository) {
        this.roleGrupoRepository = roleGrupoRepository;
    }

    @Override
    public List<RoleGrupo> buscaTodos(){
        return roleGrupoRepository.findAll();
    }

    @Override
    public RoleGrupo buscar(String nome){
        return roleGrupoRepository.findById(nome).orElseThrow(() -> new EntityNotFoundException("Role não está disponível no sistema."));
    }

    @Override
    public boolean podeConvidar(String nome) {
        RoleGrupo roleGrupo = roleGrupoRepository.findByNome(nome).orElseThrow(() -> new EntityNotFoundException());

        return roleGrupo.getPodeConvidar();
    }

    @Override
    public boolean podeRemover(String nome) {
        RoleGrupo roleGrupo = roleGrupoRepository.findByNome(nome).orElseThrow(() -> new EntityNotFoundException());

        return roleGrupo.getPodeRemover();
    }

    @Override
    public boolean podeEditar(String nome) {
        RoleGrupo roleGrupo = roleGrupoRepository.findByNome(nome).orElseThrow(() -> new EntityNotFoundException());

        return roleGrupo.getPodeEditar();
    }

}
