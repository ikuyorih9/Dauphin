package com.dauphin.dauphin.Services;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dauphin.dauphin.models.Nivel;
import com.dauphin.dauphin.services.NivelService;

@SpringBootTest
public class NivelServiceTest {
    @Autowired
    private NivelService nivelService;

    // Imprime todos os níveis que estão cadastrados na base de dados.
    @Test
    void listAllNiveisTest(){
        List<Nivel> allNiveis = nivelService.listar();
        for(Nivel nivel : allNiveis){
            System.out.println(nivel.toString());
        }
    }

    // Testa os match de uma pontuação com seu respectivo nível.
    @Test
    void findNivelByPontuacaoTest(){
        Integer []  pontuacoes = {100, 600, 1500, 4000, 7000, 11000};
        for(Integer pontuacao : pontuacoes){
            Nivel nivel = nivelService.obtemPontuacao(pontuacao);
            System.out.println("Pontuacao: " + pontuacao + " - " + nivel.toString());
        }
    }
}
