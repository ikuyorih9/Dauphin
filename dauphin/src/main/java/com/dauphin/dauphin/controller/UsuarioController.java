package com.dauphin.dauphin.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dauphin.dauphin.exception.ConflictException;
import com.dauphin.dauphin.model.Grupo;
import com.dauphin.dauphin.model.TemAmizade;
import com.dauphin.dauphin.model.Usuario;
import com.dauphin.dauphin.service.GrupoService;
import com.dauphin.dauphin.service.TemAmizadeService;
import com.dauphin.dauphin.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serial;
import java.net.http.HttpClient;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final TemAmizadeService temAmizadeService;

    public UsuarioController(UsuarioService usuarioService, TemAmizadeService temAmizadeService, GrupoService grupoService){
        this.usuarioService = usuarioService;
        this.temAmizadeService = temAmizadeService;
    }

    @GetMapping
    public List <Usuario> listarTodos(){
        return usuarioService.listar();
    }
    
    @PostMapping("cadastrar")
    public ResponseEntity cadastrarUsuario(@RequestBody Usuario usuario) {
        System.out.println("[USUARIO]: "+usuario.toString());
        usuarioService.cadastrar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("O usuário " + usuario.getUsername() + " foi cadastrado!");
    }

    @PostMapping("login")
    public ResponseEntity loginUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioAutorizado = usuarioService.autenticar(usuario.getUsername(), usuario.getSenha());
        if (usuarioAutorizado == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta!");
        return ResponseEntity.ok("Login bem sucedido!");
    }

    @PostMapping("{myUsername}/amigos")
    public ResponseEntity adicionarAmigo(@PathVariable String myUsername, @RequestBody Usuario friend){
        Usuario usuario1 = usuarioService.busca(myUsername);
        Usuario usuario2 = usuarioService.busca(friend.getUsername());
        
        if(temAmizadeService.possuemAmizade(usuario1, usuario2) == true)
            throw new ConflictException("A amizade entre " + usuario1.getNome() + " e " + usuario2.getNome() + " já existe.");

        TemAmizade novaAmizade = temAmizadeService.criar(usuario1, usuario2);

        System.out.println("[NOVA AMIZADE] " + novaAmizade);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Amizade criada com sucesso!");
        
    }

    @GetMapping("{username}/amigos")
    public List listarAmigos(@PathVariable String username) {
        return usuarioService.listarAmigos(username);
    }
    
}
