package com.dauphin.dauphin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dauphin.dauphin.dto.GrupoRequest;
import com.dauphin.dauphin.id.GrupoId;
import com.dauphin.dauphin.model.Grupo;
import com.dauphin.dauphin.service.GrupoService;

@RestController
@RequestMapping("grupos")
public class GrupoController {
    private GrupoService grupoService;

    public GrupoController(GrupoService grupoService){
        this.grupoService = grupoService;
    }

    @GetMapping("{username}/criados")
    public List <Grupo> listarGrupos(@PathVariable String username) {

        return grupoService.listar(username);
    }

    @PostMapping("{username}/criados")
    public ResponseEntity criarGrupo(@PathVariable String username, @RequestBody Grupo grupo) {
        grupoService.criar(username, grupo);

        return ResponseEntity.status(HttpStatus.CREATED).body("Grupo criado com sucesso!");
    }

    @PostMapping("criados/{username}/{id}/apagar")
    public ResponseEntity apagarGrupo(@PathVariable String username, @PathVariable Integer id){
        grupoService.apagar(new GrupoId(id, username));
        return ResponseEntity.ok("Grupo apagado.");
    }

    

}
