package com.dauphin.dauphin.Services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dauphin.dauphin.dtos.GroupNewUserDTO;
import com.dauphin.dauphin.dtos.GrupoCreateDTO;
import com.dauphin.dauphin.dtos.UsuarioCadastroDTO;
import com.dauphin.dauphin.exceptions.EntityConflictInDatabaseException;
import com.dauphin.dauphin.exceptions.EntityNotFoundInDatabaseException;
import com.dauphin.dauphin.models.Grupo;
import com.dauphin.dauphin.models.ParticipaGrupo;
import com.dauphin.dauphin.models.RoleGrupo;
import com.dauphin.dauphin.models.ParticipaGrupo.ParticipaGrupoId;
import com.dauphin.dauphin.models.Usuario;
import com.dauphin.dauphin.repositories.GrupoRepository;
import com.dauphin.dauphin.repositories.ParticipaGrupoRepository;
import com.dauphin.dauphin.repositories.UsuarioRepository;
import com.dauphin.dauphin.services.GrupoService;
import com.dauphin.dauphin.services.ParticipaGrupoService;
import com.dauphin.dauphin.services.UsuarioService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ParticipaGrupoServiceTest {
    @Autowired
    ParticipaGrupoService participaGrupoService;
    @Autowired
    ParticipaGrupoRepository participaGrupoRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    GrupoService grupoService;
    @Autowired
    GrupoRepository grupoRepository;

    private Usuario host;
    private Usuario admin;
    private Usuario member;
    private Grupo grupo;

    @BeforeEach
    void createParticipation(){
        try{
            host = usuarioService.cadastrar(new UsuarioCadastroDTO(
                "host", 
                "host", 
                "host", 
                "host", 
                "M", 
                LocalDate.now(), 
                "foto"
            ));         
        }
        catch (EntityConflictInDatabaseException e){}

        try{
            admin = usuarioService.cadastrar(new UsuarioCadastroDTO(
                "admin", 
                "admin", 
                "admin", 
                "admin", 
                "M", 
                LocalDate.now(), 
                "foto"
            ));         
        }
        catch (EntityConflictInDatabaseException e){}

        try{
            member = usuarioService.cadastrar(new UsuarioCadastroDTO(
                "member", 
                "member", 
                "member", 
                "member", 
                "M", 
                LocalDate.now(), 
                "foto"
            ));         
        }
        catch (EntityConflictInDatabaseException e){}

        try{
            grupo = grupoService.criar(new GrupoCreateDTO(
                "admin", 
                "nome", 
                1, 
                null
            ));
        }
        catch(EntityConflictInDatabaseException e){}

        ParticipaGrupo hostPart = participaGrupoService.criar(new GroupNewUserDTO(host.getUsername(), grupo.getId(), "HOST"));
        ParticipaGrupo adminPart = participaGrupoService.criar(new GroupNewUserDTO(admin.getUsername(), grupo.getId(), "ADMIN"));
        ParticipaGrupo memberPart = participaGrupoService.criar(new GroupNewUserDTO(member.getUsername(), grupo.getId(), "MEMBER"));
        
        assertTrue(participaGrupoService.buscaParticipantes(grupo.getId()).contains(hostPart));
        assertTrue(participaGrupoService.buscaParticipantes(grupo.getId()).contains(adminPart));
        assertTrue(participaGrupoService.buscaParticipantes(grupo.getId()).contains(memberPart));

    }

    @Test
    @Transactional
    void createNewParticipacaoTest(){
        Usuario usuario = null;
        try{
            usuario = usuarioService.cadastrar(new UsuarioCadastroDTO(
                "username", 
                "email", 
                "nome", 
                "senha", 
                "M", 
                LocalDate.now(), 
                "foto"
            ));         
        }
        catch (EntityConflictInDatabaseException e){
            usuario = usuarioService.buscar("username");
        }

        Grupo grupo = null;
        grupoService.criar(new GrupoCreateDTO(
            usuario.getUsername(), 
            "nome", 
            1, 
            null
        ));
        for(Grupo g : grupoRepository.findAll()){
            grupo = g;
        }

        ParticipaGrupo part = participaGrupoService.criar(new GroupNewUserDTO(
            usuario.getUsername(), 
            grupo.getId(), 
            "MEMBER"
        ));

        Optional<ParticipaGrupo> p = participaGrupoRepository.findById(new ParticipaGrupoId(usuario.getUsername(), grupo.getId()));
        
        assertTrue(p.isPresent());
        assertEquals(part, p.get());
    }

    @Test
    @Transactional
    void getRoleOfTheParticipationTest(){
        Usuario usuario = null;
        try{
            usuario = usuarioService.cadastrar(new UsuarioCadastroDTO(
                "username", 
                "email", 
                "nome", 
                "senha", 
                "M", 
                LocalDate.now(), 
                "foto"
            ));         
        }
        catch (EntityConflictInDatabaseException e){
            usuario = usuarioService.buscar("username");
        }

        Grupo grupo = grupoService.criar(new GrupoCreateDTO(
            usuario.getUsername(), 
            "nome", 
            1, 
            null
        ));

        participaGrupoService.criar(new GroupNewUserDTO(usuario.getUsername(), grupo.getId(), "ADMIN"));

        List<ParticipaGrupo> list = participaGrupoService.buscaParticipantes(grupo.getId());
        assertFalse(list.isEmpty());

        final String username = usuario.getUsername();
        final Integer id = grupo.getId();
        assertDoesNotThrow(() -> {
            RoleGrupo role = participaGrupoService.buscaRole(new ParticipaGrupoId(username, id));
            System.out.println(role.toString());
        });

    }

    @Test
    @Transactional
    void removeUserFromGroup(){
        participaGrupoService.remover(new ParticipaGrupoId(member.getUsername(), grupo.getId()), "admin");
        assertThrows(EntityNotFoundInDatabaseException.class, () -> {
            participaGrupoService.buscar(new ParticipaGrupoId(member.getUsername(), grupo.getId()));
        });
    }

    @AfterEach
    void removeAll(){
        usuarioRepository.deleteAll();
        grupoRepository.deleteAll();
        participaGrupoRepository.deleteAll();
    }
}
