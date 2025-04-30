package com.dauphin.dauphin.models;

import java.time.LocalDate;

import com.dauphin.dauphin.interfaces.ID;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class ParticipaGrupo {
    @EmbeddedId
    private ParticipaGrupoId id;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name="usuario", referencedColumnName = "username")
    private Usuario usuario;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name="grupo", referencedColumnName = "id")
    private Grupo grupo;

    LocalDate dataEntrada;

    @ManyToOne
    @JoinColumn(name="role", referencedColumnName = "nome")
    private RoleGrupo role;


    public ParticipaGrupo() {
    }


    public ParticipaGrupo(Usuario usuario, Grupo grupo, LocalDate dataEntrada, RoleGrupo role) {
        this.id = new ParticipaGrupoId(usuario.getUsername(), grupo.getId());
        this.usuario = usuario;
        this.grupo = grupo;
        this.dataEntrada = dataEntrada;
        this.role = role;
    }


    public ParticipaGrupoId getId() {
        return id;
    }


    public void setId(ParticipaGrupoId id) {
        this.id = id;
    }


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public Grupo getGrupo() {
        return grupo;
    }


    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }


    public LocalDate getDataEntrada() {
        return dataEntrada;
    }


    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }


    public RoleGrupo getRole() {
        return role;
    }

    public void setRole(RoleGrupo role) {
        this.role = role;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
        result = prime * result + ((dataEntrada == null) ? 0 : dataEntrada.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
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
        ParticipaGrupo other = (ParticipaGrupo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        if (grupo == null) {
            if (other.grupo != null)
                return false;
        } else if (!grupo.equals(other.grupo))
            return false;
        if (dataEntrada == null) {
            if (other.dataEntrada != null)
                return false;
        } else if (!dataEntrada.equals(other.dataEntrada))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "ParticipaGrupo [usuario=" + usuario.getUsername() + ", grupo=" + grupo.getId() + ", dataEntrada=" + dataEntrada
                + ", role=" + role.getNome() + "]";
    }

    @Embeddable
    public static class ParticipaGrupoId{
        String username;
        Integer id;

        public ParticipaGrupoId() {
        }
        
        public ParticipaGrupoId(String username, Integer id) {
            this.username = username;
            this.id = id;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        
    }

    
}
