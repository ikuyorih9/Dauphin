package com.dauphin.dauphin.services;

import java.util.List;

import com.dauphin.dauphin.models.Nivel;

public interface NivelService {
    List<Nivel> listar();
    public Nivel obtemPontuacao(Integer pontuacao);
}
