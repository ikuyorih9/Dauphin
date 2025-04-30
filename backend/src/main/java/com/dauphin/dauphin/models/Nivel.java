package com.dauphin.dauphin.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Nivel {
    @Id
    private String classificacao;
    private Integer minimoPontos;
    private String descricao;

    
    public Nivel() {}

    public Nivel(String classificacao, Integer minimoPontos, String descricao) {
        this.classificacao = classificacao;
        this.minimoPontos = minimoPontos;
        this.descricao = descricao;
    }
    
    public String getClassificacao() {
        return classificacao;
    }
    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }
    public Integer getMinimoPontos() {
        return minimoPontos;
    }
    public void setMinimoPontos(Integer minimoPontos) {
        this.minimoPontos = minimoPontos;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Nivel [classificacao=" + classificacao + ", minimoPontos=" + minimoPontos + ", descricao=" + descricao + "]";
    }
}
