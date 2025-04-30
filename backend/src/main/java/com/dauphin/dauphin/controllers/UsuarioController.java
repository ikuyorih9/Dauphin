package com.dauphin.dauphin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dauphin.dauphin.dtos.FriendshipCreateRequestDTO;
import com.dauphin.dauphin.dtos.GroupNewUserDTO;
import com.dauphin.dauphin.dtos.GrupoCreateDTO;
import com.dauphin.dauphin.exceptions.PermissionDeniedException;
import com.dauphin.dauphin.models.Grupo;
import com.dauphin.dauphin.models.Usuario;
import com.dauphin.dauphin.models.ParticipaGrupo.ParticipaGrupoId;
import com.dauphin.dauphin.models.TemAmizade.TemAmizadeId;
import com.dauphin.dauphin.services.GrupoParticipacaoService;
import com.dauphin.dauphin.services.GrupoService;
import com.dauphin.dauphin.services.ParticipaGrupoService;
import com.dauphin.dauphin.services.TemAmizadeService;
import com.dauphin.dauphin.services.UsuarioService;

import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("users")
public class UsuarioController {

    private UsuarioService usuarioService;
    private TemAmizadeService temAmizadeService;
    private GrupoService grupoService;
    private ParticipaGrupoService participaGrupoService;
    private GrupoParticipacaoService grupoParticipacaoService;

    public UsuarioController(UsuarioService usuarioService, TemAmizadeService temAmizadeService, GrupoService grupoService, ParticipaGrupoService participaGrupoService, GrupoParticipacaoService grupoParticipacaoService) {
        this.usuarioService = usuarioService;
        this.temAmizadeService = temAmizadeService;
        this.grupoService = grupoService;
        this.participaGrupoService = participaGrupoService;
        this.grupoParticipacaoService = grupoParticipacaoService;
    }

    // Lista os usuários da base de dados.
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.lista();
    }    

    // Remove o usuário da base de dados.
    @DeleteMapping("me")
    public ResponseEntity<String> deletarConta(@RequestBody String username){
        usuarioService.deletar(username); // Chama o serviço para remover o usuário da base.
        return ResponseEntity.ok("Usuário foi deletado com sucesso!");
    }

    // Cria amizade entre usuários.
    @PostMapping("friends")
    public ResponseEntity<String> criarAmizade(@RequestBody String toUsername, @AuthenticationPrincipal UserDetails userDetails) {
        temAmizadeService.criar(userDetails.getUsername(), toUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body("Amizade foi criada com sucesso!");
    }

    @DeleteMapping("friends")
    public ResponseEntity<String> apagarAmizade(@RequestBody String friendUsername, @AuthenticationPrincipal UserDetails userDetails) {
        temAmizadeService.apagar(userDetails.getUsername(), friendUsername);
        return ResponseEntity.ok("Amizade foi apagada com sucesso!");
    }
    
    // Cria um grupo.
    @PostMapping("group")
    public ResponseEntity<String> criarGrupo(@RequestBody GrupoCreateDTO request, @AuthenticationPrincipal UserDetails userDetails) {
        grupoParticipacaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Grupo foi criado com sucesso!");
    }
    // Cria um token para entrar no grupo.
    @GetMapping("group/{id}/invite")
    public String gerarConvite(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        return grupoParticipacaoService.gerarConvite(id);
    }
    
    // Participa de um grupo.
    @PostMapping("group/join")
    public ResponseEntity<String> entrarGrupo(@RequestBody String token) {
        grupoParticipacaoService.aceitarConvite(token);
        return ResponseEntity.status(HttpStatus.OK).body("Você entrou no grupo!");
    }

    // Remover integrante.
    @PostMapping("group/{id}/remove")
    public ResponseEntity<String> removerUsuario(@PathVariable Integer id, @RequestBody String username, @AuthenticationPrincipal UserDetails userDetails) {
        participaGrupoService.remover(new ParticipaGrupoId(username,id), userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body("Usuario removido do grupo!");
    }
    
    // Mudar ROLE.
    @PostMapping("group/{id}/role")
    public ResponseEntity<String> mudarRoleUsuario(@RequestBody String username){
        return ResponseEntity.status(HttpStatus.OK).body("Você entrou no grupo!");
    }

    // Apaga um grupo.
    @DeleteMapping("group/delete")
    public ResponseEntity<String> deletarGrupo(@RequestBody Integer id){
        grupoParticipacaoService.apagar(id);
        return ResponseEntity.ok("Grupo foi apagado com sucesso!");
    }    
}
