package com.dauphin.dauphin.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.dauphin.dauphin.id.TemAmizadeId;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tem_Amizade")
public class TemAmizade {
    @EmbeddedId
    private TemAmizadeId id;

    @ManyToOne
    @MapsId("username1")
    @JoinColumn(name = "usuario1", referencedColumnName = "username", nullable = false)
    private Usuario usuario1;

    @ManyToOne
    @MapsId("username2")
    @JoinColumn(name = "usuario2", referencedColumnName = "username", nullable = false)
    private Usuario usuario2;

    @Column(nullable = false)
    private LocalDate dataInicio;
    public TemAmizade(){}

    public TemAmizade(Usuario usuario1, Usuario usuario2, LocalDate dataInicio) {    
        id = new TemAmizadeId(usuario1.getUsername(), usuario2.getUsername());    
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public Usuario getUsuario2() {
        return usuario2;
    }

    public void setUsuario2(Usuario usuario2) {
        this.usuario2 = usuario2;
    }

}
