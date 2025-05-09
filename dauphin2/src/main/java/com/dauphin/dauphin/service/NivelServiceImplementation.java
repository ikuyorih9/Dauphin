package com.dauphin.dauphin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dauphin.dauphin.model.Nivel;
import com.dauphin.dauphin.repository.NivelRepository;

@Service
public class NivelServiceImplementation implements NivelService{
    private NivelRepository nivelRepository;

    public NivelServiceImplementation(NivelRepository nivelRepository){
        this.nivelRepository = nivelRepository;
    }

    @Override
    public Nivel determinaNivel(Integer pontuacao) {
        return nivelRepository.determinaNivel(pontuacao).orElseThrow(() -> new RuntimeException("Nenhum nível encontrado"));
    }

}
