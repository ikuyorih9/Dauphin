package com.dauphin.dauphin.model;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;

import com.dauphin.dauphin.id.GrupoId;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Grupo")
@IdClass(GrupoId.class)
@DynamicInsert
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grupo_seq")
    @SequenceGenerator(name = "grupo_seq", sequenceName = "grupo_id_seq", allocationSize = 1)
    private Integer id;

    @Id
    @ManyToOne
    @JoinColumn(name = "usuario_host", referencedColumnName = "username")
    private Usuario usuarioHost;

    private LocalDate dataCriacao;

    @Column(nullable = false)
    private String nome;

    @Column(insertable = false)
    private Integer qtdMaxima;

    public Grupo(){}

    public Grupo(Integer id, Usuario usuarioHost, String nome, Integer qtdMaxima){
        this.id = id;
        this.usuarioHost = usuarioHost;
        this.nome = nome;
        this.qtdMaxima = qtdMaxima;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuarioHost() {
        return usuarioHost;
    }

    public void setUsuarioHost(Usuario usuarioHost) {
        this.usuarioHost = usuarioHost;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
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

    public String toString() {
        return "Grupo{" +
                "id=" + id +
                "userhost=" + usuarioHost.getUsername() +
                ", dataCriacao=" + dataCriacao +
                ", nome='" + nome + '\'' +
                ", qtdMaxima=" + qtdMaxima +
                '}';
    }
}
