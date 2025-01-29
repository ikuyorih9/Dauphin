package com.dauphin.dauphin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String senha;

    @Column(nullable =  false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private Integer idade;
    
    @Column(nullable = true)
    private String sexo;

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter para idade
    public Integer getIdade() {
        return idade;
    }

    // Setter para idade
    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    // Getter para sexo
    public String getSexo() {
        return sexo;
    }

    // Setter para sexo
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void printUsuario(){
        System.out.println("[USER] id: " + id);
        System.out.println("[USER] usuario: " + username);
        System.out.println("[USER] nome: " + nome);
        System.out.println("[USER] email: " + email);
        System.out.println("[USER] senha: " + senha);
        System.out.println("[USER] idade: " + idade);
        System.out.println("[USER] sexo: " + sexo);
        
    }
    
}