package com.dauphin.dauphin.models;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class TemAmizade {
    @EmbeddedId
    private TemAmizadeId id;
    private LocalDate dataInicio;

    @ManyToOne
    @MapsId("username1") // Mapeia o nome da String dentro do EmbeddedId.
    @JoinColumn(name = "usuario1", referencedColumnName = "username") // Mapeia o nome 'usuario1' da tabela tem_amizade para o 'username' da tabela Usuario.
    private Usuario usuario1;

    @ManyToOne
    @MapsId("username2") // Mapeia o nome da String dentro do EmbeddedId.
    @JoinColumn(name = "usuario2", referencedColumnName = "username") // Mapeia o nome 'usuario1' da tabela tem_amizade para o 'username' da tabela Usuario.
    private Usuario usuario2;
    
    public TemAmizade() {}
    
    public TemAmizade(Usuario usuario1, Usuario usuario2, LocalDate dataInicio) {
        this.id = new TemAmizadeId(usuario1.getUsername(), usuario2.getUsername());
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.dataInicio = dataInicio;
    }

    public TemAmizadeId getId() {
        return id;
    }

    public void setId(TemAmizadeId id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    };

    @Embeddable
    public static class TemAmizadeId implements Serializable{
        String username1;
        String username2;
        public TemAmizadeId() {}

        public TemAmizadeId(String username1, String username2) {
            this.username1 = username1;
            this.username2 = username2;
        }

        public String getUsername1() {
            return username1;
        }

        public void setUsername1(String username1) {
            this.username1 = username1;
        }

        public String getUsername2() {
            return username2;
        }

        public void setUsername2(String username2) {
            this.username2 = username2;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((username1 == null) ? 0 : username1.hashCode());
            result = prime * result + ((username2 == null) ? 0 : username2.hashCode());
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
            TemAmizadeId other = (TemAmizadeId) obj;
            if (username1 == null) {
                if (other.username1 != null)
                    return false;
            } else if (!username1.equals(other.username1))
                return false;
            if (username2 == null) {
                if (other.username2 != null)
                    return false;
            } else if (!username2.equals(other.username2))
                return false;
            return true;
        }
    }
}

