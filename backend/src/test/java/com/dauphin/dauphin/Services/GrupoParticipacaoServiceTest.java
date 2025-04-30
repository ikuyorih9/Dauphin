package com.dauphin.dauphin.Services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dauphin.dauphin.constants.GrupoRolesConsts;
import com.dauphin.dauphin.dtos.GroupNewUserDTO;
import com.dauphin.dauphin.dtos.GrupoCreateDTO;
import com.dauphin.dauphin.dtos.UsuarioCadastroDTO;
import com.dauphin.dauphin.exceptions.EntityConflictInDatabaseException;
import com.dauphin.dauphin.exceptions.PermissionDeniedException;
import com.dauphin.dauphin.models.Grupo;
import com.dauphin.dauphin.models.ParticipaGrupo;
import com.dauphin.dauphin.models.ParticipaGrupo.ParticipaGrupoId;
import com.dauphin.dauphin.models.Usuario;
import com.dauphin.dauphin.repositories.GrupoRepository;
import com.dauphin.dauphin.repositories.ParticipaGrupoRepository;
import com.dauphin.dauphin.repositories.UsuarioRepository;
import com.dauphin.dauphin.services.GrupoParticipacaoService;
import com.dauphin.dauphin.services.GrupoService;
import com.dauphin.dauphin.services.ParticipaGrupoService;
import com.dauphin.dauphin.services.UsuarioService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class GrupoParticipacaoServiceTest {
    @Autowired
    GrupoParticipacaoService grupoParticipacaoService;
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
    
    void authenticateAs(String username) {
        var auth = new UsernamePasswordAuthenticationToken(username, null, List.of());
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }

    @BeforeEach
    void setupAuthentication() {
        authenticateAs("host");
    }

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
            
            System.out.println("[HOST]: " + host.toString());
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
            System.out.println("[ADMIN]: " + admin.toString());   
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
            System.out.println("[MEMBER]: " + member.toString());  
        }
        catch (EntityConflictInDatabaseException e){}

        try{
            grupo = grupoParticipacaoService.criar(new GrupoCreateDTO(
                "admin", 
                "nome", 
                1, 
                null
            ));
            
            System.out.println("[GRUPO]: " + grupo.toString());
        }
        catch(EntityConflictInDatabaseException e){}

        ParticipaGrupo adminPart = participaGrupoService.criar(new GroupNewUserDTO(admin.getUsername(), grupo.getId(), "ADMIN"));
        ParticipaGrupo memberPart = participaGrupoService.criar(new GroupNewUserDTO(member.getUsername(), grupo.getId(), "MEMBER"));
        
        assertTrue(participaGrupoService.buscaParticipantes(grupo.getId()).contains(adminPart));
        assertTrue(participaGrupoService.buscaParticipantes(grupo.getId()).contains(memberPart));

    }

    @AfterEach
    void removeAll(){
        usuarioRepository.deleteAll();
        grupoRepository.deleteAll();
        participaGrupoRepository.deleteAll();
    }
    
    @Test
    @Transactional
    void createGroupAndParticipationTest(){
        Grupo g = grupoParticipacaoService.criar(
            new GrupoCreateDTO("host", "grupinho", 2, "PUBLICO")
        );

        assertEquals(g, grupoService.buscar(g.getId()));
    }

    @Test
    @Transactional
    void changeRoleOfMemberTest(){
        authenticateAs("member"); // Muda o usuário autenticado para MEMBER
        // Verifica se PermissionDeniedException é lançado.
        assertThrows(PermissionDeniedException.class, ()->{
            // Tenta alterar as permissões do HOST, sendo MEMBER.
            grupoParticipacaoService.alterarRole(grupo.getId(), host.getUsername(), GrupoRolesConsts.ADMIN);
        });

        authenticateAs("host"); // Muda o usuário autenticado para HOST
        assertDoesNotThrow(()->{ // Verifica se nenhuma exceção é lançada.
            // Tenta alterar as permissões de MEMBRO, sendo HOST.
            grupoParticipacaoService.alterarRole(grupo.getId(), member.getUsername(), GrupoRolesConsts.ADMIN);
        });

        // Obtém a participação do MEMBER.
        ParticipaGrupo p = participaGrupoService.buscar(new ParticipaGrupoId(member.getUsername(), grupo.getId()));
        
        // Verifica se ele se tornou ADMIN.
        assertTrue(p.getRole().getNome().equals(GrupoRolesConsts.ADMIN));
    }

    
}
