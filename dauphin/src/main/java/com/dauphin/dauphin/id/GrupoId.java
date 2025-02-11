package com.dauphin.dauphin.id;

import java.io.Serializable;

import com.dauphin.dauphin.model.Usuario;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

public class GrupoId implements Serializable {
    private Integer id;
    private String usuarioHost; // Armazena apenas o username

    public GrupoId(){}

    public GrupoId(Integer id, String usuarioHost){
        this.id = id;
        this.usuarioHost = usuarioHost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuarioHost() {
        return usuarioHost;
    }

    public void setUsuarioHost(String usuarioHost) {
        this.usuarioHost = usuarioHost;
    }
}
