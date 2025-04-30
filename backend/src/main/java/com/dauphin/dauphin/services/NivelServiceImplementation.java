package com.dauphin.dauphin.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dauphin.dauphin.models.Nivel;
import com.dauphin.dauphin.repositories.NivelRepository;

@Service
public class NivelServiceImplementation implements NivelService{
    private NivelRepository nivelRepository;

    public NivelServiceImplementation(NivelRepository nivelRepository) {
        this.nivelRepository = nivelRepository;
    }

    @Override
    public List<Nivel> listar() {
        return nivelRepository.findAll();
    }
    @Override
    public Nivel obtemPontuacao(Integer pontuacao){
        return nivelRepository.findClassificacaoByPontuacao(pontuacao).orElse(null);
    }
}
