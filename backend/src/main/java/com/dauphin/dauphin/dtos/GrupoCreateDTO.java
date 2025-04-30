package com.dauphin.dauphin.dtos;

public class GrupoCreateDTO {
    Integer id;
    String username;
    String nome;
    Integer qtdMaxima;
    String visibilidade;

    public GrupoCreateDTO() {
    }

    public GrupoCreateDTO(Integer id, String username, String nome, Integer qtdMaxima, String visibilidade) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.qtdMaxima = qtdMaxima;
        this.visibilidade = visibilidade;
    }

    public GrupoCreateDTO(String username, String nome, Integer qtdMaxima, String visibilidade) {
        this.id = null;
        this.username = username;
        this.nome = nome;
        this.qtdMaxima = qtdMaxima;
        this.visibilidade = visibilidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdMaxima() {
        return qtdMaxima;
    }
    
    public void setQtdMaxima(Integer qtdMaxima) {
        this.qtdMaxima = qtdMaxima;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(String visibilidade) {
        this.visibilidade = visibilidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
