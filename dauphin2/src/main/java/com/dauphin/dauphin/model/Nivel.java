package com.dauphin.dauphin.model;

import org.hibernate.annotations.Columns;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Nivel {
    @Id
    private String classificacao;

    @Column(nullable = false)
    private Integer minimoPontos;

    @Column(nullable = false)
    private String descricao;

    public Nivel(){}

    public Nivel(String classificacao, Integer minimoPontos, String descricao){
        this.classificacao = classificacao;
        this.minimoPontos = minimoPontos;
        this.descricao = descricao;
    }

    // Getters
    public String getClassificacao() {
        return classificacao;
    }

    public Integer getMinimoPontos() {
        return minimoPontos;
    }

    public String getDescricao() {
        return descricao;
    }
}
