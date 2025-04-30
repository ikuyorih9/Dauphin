package com.dauphin.dauphin.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RoleGrupo {
    @Id
    String nome;
    String descricao;
    Boolean podeConvidar;
    Boolean podeRemover;
    Boolean podeEditar;
    Boolean podeApagar;
    
    public RoleGrupo() {
    }

    public RoleGrupo(String nome, String descricao, Boolean podeConvidar, Boolean podeRemover, Boolean podeEditar, Boolean podeApagar) {
        this.nome = nome;
        this.descricao = descricao;
        this.podeConvidar = podeConvidar;
        this.podeRemover = podeRemover;
        this.podeEditar = podeEditar;
        this.podeApagar = podeApagar;
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Boolean getPodeConvidar() {
        return podeConvidar;
    }
    public void setPodeConvidar(Boolean podeConvidar) {
        this.podeConvidar = podeConvidar;
    }
    public Boolean getPodeRemover() {
        return podeRemover;
    }
    public void setPodeRemover(Boolean podeRemover) {
        this.podeRemover = podeRemover;
    }
    public Boolean getPodeEditar() {
        return podeEditar;
    }
    public void setPodeEditar(Boolean podeEditar) {
        this.podeEditar = podeEditar;
    }

    public Boolean getPodeApagar() {
        return podeApagar;
    }

    public void setPodeApagar(Boolean podeApagar) {
        this.podeApagar = podeApagar;
    }
    
    @Override
    public String toString() {
        return "RoleGrupo [nome=" + nome + ", descricao=" + descricao + ", podeConvidar=" + podeConvidar
                + ", podeRemover=" + podeRemover + ", podeEditar=" + podeEditar + "]";
    }

    

    
}
