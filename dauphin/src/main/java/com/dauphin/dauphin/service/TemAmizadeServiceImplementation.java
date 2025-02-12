package com.dauphin.dauphin.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dauphin.dauphin.exception.UserNotFoundException;
import com.dauphin.dauphin.model.TemAmizade;
import com.dauphin.dauphin.model.Usuario;
import com.dauphin.dauphin.repository.TemAmizadeRepository;

import jakarta.transaction.Transactional;

@Service
public class TemAmizadeServiceImplementation implements TemAmizadeService{
    @Autowired
    private TemAmizadeRepository temAmizadeRepository;

    @Override
    public boolean possuemAmizade(Usuario usuario1, Usuario usuario2){
        if(usuario1 == null)
            throw new RuntimeException("[RUNTIME EXCEPTION] Usuario usuario1 é NullPointer.");
        if(usuario2 == null)
            throw new RuntimeException("[RUNTIME EXCEPTION] Usuario usuario2 é NullPointer.");

        TemAmizade temAmizade = temAmizadeRepository.temAmizade(usuario1.getUsername(), usuario2.getUsername());
        if(temAmizade != null)
            return true;
        return false;
    }

    @Override
    public TemAmizade criar(Usuario usuario1, Usuario usuario2){
        if(usuario1 == null)
            throw new RuntimeException("[RUNTIME EXCEPTION] Usuario usuario1 é NullPointer.");
        if(usuario2 == null)
            throw new RuntimeException("[RUNTIME EXCEPTION] Usuario usuario2 é NullPointer.");

        System.out.println("[CREATING] Criando amizade entre " + usuario1.getUsername() + " e " + usuario2.getUsername());
        TemAmizade amizade = new TemAmizade(usuario1, usuario2, LocalDate.now());
        System.out.println("[CREATED] Nova amizade criada: " + amizade.getUsuario1().getUsername() + " e " + amizade.getUsuario2().getUsername() + " na data " + amizade.getDataInicio());
        return temAmizadeRepository.save(amizade);
    }

    @Override
    public List listar(String username){
        return temAmizadeRepository.buscarAmizades(username);
    }
}
