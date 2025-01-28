package com.dauphin.dauphin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dauphin.dauphin.dto.LoginRequest;
import com.dauphin.dauphin.model.Usuario;
import com.dauphin.dauphin.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login recebido: " + loginRequest.getUsername());
        System.out.println("Senha recebida: " + loginRequest.getSenha());
        
        // Buscar o usuário pelo nome de usuário
        Optional<Usuario> user = userRepository.findByUsername(loginRequest.getUsername());

        if (user.isPresent() && user.get().getSenha().equals(loginRequest.getSenha())) {
            // Usuário encontrado e senha correta
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            // Usuário não encontrado ou senha incorreta
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos.");
        }
    }
}