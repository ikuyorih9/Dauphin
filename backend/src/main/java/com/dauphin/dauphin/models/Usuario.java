package com.dauphin.dauphin.models;


import java.time.LocalDate;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Usuario {
    @Id
    String username;
    String email;
    String nome;
    String senha;
    String sexo;
    LocalDate dataNascimento;
    LocalDate dataCadastro;
    String foto;
    Integer pontuacao;
    String role;
    
    @ManyToOne
    @JoinColumn(name="nivel")
    Nivel nivel;
    
    public Usuario(){}

    public Usuario(String username, String email, String nome, String senha, String sexo, LocalDate dataNascimento,
            LocalDate dataCadastro, String foto, Integer pontuacao, String role, Nivel nivel) {
        this.username = username;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = dataCadastro;
        this.foto = foto;
        this.pontuacao = pontuacao;
        this.role = role;
        this.nivel = nivel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Usuario [username=" + username + ", email=" + email + ", nome=" + nome + ", senha=" + senha + ", sexo="
                + sexo + ", dataNascimento=" + dataNascimento + ", dataCadastro=" + dataCadastro + ", foto=" + foto
                + ", pontuacao=" + pontuacao + ", role=" + role + ", nivel=" + nivel.getClassificacao() + "]";
    }

}
