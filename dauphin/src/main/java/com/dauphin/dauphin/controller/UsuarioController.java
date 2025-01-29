package com.dauphin.dauphin.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.dauphin.dauphin.dto.ApiResponse;
import com.dauphin.dauphin.dto.ErroResponse;
import com.dauphin.dauphin.model.Usuario;
import com.dauphin.dauphin.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List <Usuario> listarTodos(){
        return usuarioService.listarTodos();
    }

    // Endpoint para adicionar um novo usuário (sem validações específicas)
    @PostMapping("/adicionar")
    public Usuario adicionarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    // Endpoint para cadastrar um novo usuário com validações
    @PostMapping("/cadastrar")
    public ResponseEntity<ApiResponse<Usuario>> cadastrarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
        ApiResponse apiResponse = new ApiResponse<>(HttpStatus.OK, "Usuario cadastrado com sucesso!", usuarioCadastrado);
        return ResponseEntity.ok(apiResponse);
    }
    
    
}
