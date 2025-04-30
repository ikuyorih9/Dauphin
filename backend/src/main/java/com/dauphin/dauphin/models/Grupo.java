package com.dauphin.dauphin.models;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.annotation.Generated;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "grupo_seq", sequenceName = "grupo_id_seq", allocationSize = 1)
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "host", referencedColumnName = "username")
    private Usuario usuarioHost;

    private LocalDate dataCriacao;
    private String nome;
    private Integer qtdMaxima;
    private String visibilidade;

    public Grupo() {
    }

    public Grupo(Integer id, Usuario usuarioHost, LocalDate dataCriacao, String nome, Integer qtdMaxima, String visibilidade) {
        this.id = id;
        this.usuarioHost = usuarioHost;
        this.dataCriacao = dataCriacao;
        this.nome = nome;
        this.qtdMaxima = qtdMaxima;
        this.visibilidade = visibilidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Embeddable
    public static class GrupoID implements Serializable{
        String host;
        Integer id;
        
        public GrupoID() {
        }

        public GrupoID(String host, Integer id) {
            this.host = host;
            this.id = id;
        }
        
        public String getHost() {
            return host;
        }
        public void setHost(String host) {
            this.host = host;
        }
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((host == null) ? 0 : host.hashCode());
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            GrupoID other = (GrupoID) obj;
            if (host == null) {
                if (other.host != null)
                    return false;
            } else if (!host.equals(other.host))
                return false;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
        }
        @Override
        public String toString() {
            return "GrupoID [host=" + host + ", id=" + id + "]";
        }
    }

    @Override
    public String toString() {
        return "Grupo [id=" + (id != null ? id.toString() : "null") + ", host=" + usuarioHost.getUsername() + ", dataCriacao=" + dataCriacao + ", nome=" + nome + ", qtdMaxima="+ qtdMaxima + "]";
    }

    public Usuario getUsuarioHost() {
        return usuarioHost;
    }

    public void setUsuarioHost(Usuario usuarioHost) {
        this.usuarioHost = usuarioHost;
    }

    public String getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(String visibilidade) {
        this.visibilidade = visibilidade;
    }
}
