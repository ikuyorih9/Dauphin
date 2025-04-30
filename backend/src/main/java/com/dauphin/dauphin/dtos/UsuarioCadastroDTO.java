package com.dauphin.dauphin.dtos;

import java.time.LocalDate;

public class UsuarioCadastroDTO {
    String username;
    String email;
    String nome;
    String senha;
    String sexo;
    LocalDate dataNascimento;
    String foto;

    public UsuarioCadastroDTO(String username, String email, String nome, String senha, String sexo, LocalDate dataNascimento, String foto) {
        this.username = username;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.foto = foto;
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
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }

}
