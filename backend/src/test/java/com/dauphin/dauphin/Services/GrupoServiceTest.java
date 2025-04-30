package com.dauphin.dauphin.Services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dauphin.dauphin.dtos.GrupoCreateDTO;
import com.dauphin.dauphin.dtos.UsuarioCadastroDTO;
import com.dauphin.dauphin.exceptions.TokenFailedException;
import com.dauphin.dauphin.models.Grupo;
import com.dauphin.dauphin.repositories.GrupoRepository;
import com.dauphin.dauphin.services.GrupoService;
import com.dauphin.dauphin.services.UsuarioService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class GrupoServiceTest {
    @Autowired
    GrupoService grupoService;
    @Autowired
    GrupoRepository grupoRepository;
    @Autowired
    UsuarioService usuarioService;
    
    
    @Test
    @Transactional
    void createNewGroupTest(){
        // Cria um DTO de usuário para teste.
        UsuarioCadastroDTO userDTO = new UsuarioCadastroDTO(
			"user",
			"user@email.com", 
			"user", 
			"user", 
			"M", 
			LocalDate.now(), 
			"user"
		);

        // Cadastrar o usuário através do service.
        usuarioService.cadastrar(userDTO);

        GrupoCreateDTO groupDTO = new GrupoCreateDTO(userDTO.getUsername(), "Group", 2, null);
        Grupo created = grupoService.criar(groupDTO);

        List<Grupo> databaseGroups = grupoRepository.findByHost(userDTO.getUsername());
        assertTrue(databaseGroups.contains(created));
    }

    @Test
    @Transactional
    void deleteGroupTest(){
        // Cria um DTO de usuário para teste.
        UsuarioCadastroDTO userDTO = new UsuarioCadastroDTO(
			"user",
			"user@email.com", 
			"user", 
			"user", 
			"M", 
			LocalDate.now(), 
			"user"
		);

        // Cadastrar o usuário através do service.
        usuarioService.cadastrar(userDTO);

        GrupoCreateDTO groupDTO = new GrupoCreateDTO(userDTO.getUsername(), "Group", 2, null);
        grupoService.criar(groupDTO);

        List<Grupo> databaseGroups = grupoRepository.findAll();
        assertFalse(databaseGroups.isEmpty());

        for(Grupo g : databaseGroups){
            grupoService.apagar(g.getId());
        }

        databaseGroups = grupoRepository.findAll();
        assertTrue(databaseGroups.isEmpty());
    }

    @Test
    @Transactional
    void generateAndValidateInvite(){
        // Cria um DTO de usuário para teste.
        UsuarioCadastroDTO userDTO = new UsuarioCadastroDTO(
			"user",
			"user@email.com", 
			"user", 
			"user", 
			"M", 
			LocalDate.now(), 
			"user"
		);
        // Cadastrar o usuário através do service.
        usuarioService.cadastrar(userDTO);

        // Cria o grupo na base de dados
        GrupoCreateDTO groupDTO = new GrupoCreateDTO(userDTO.getUsername(), "Group", 2, null);
        grupoService.criar(groupDTO);

        List<Grupo> databaseGroups = grupoRepository.findAll();
        assertFalse(databaseGroups.isEmpty());

        Integer id;
        for(Grupo g : databaseGroups){
            id = g.getId();
            String token = grupoService.gerarConvite(id, GrupoService.EXPIRATION_TIME);
            System.out.println("[TOKEN]: " + token);

            Integer parsedId = (int) grupoService.aceitarConvite(token);
            System.out.println("[PARSED ID]: " + parsedId);
            assertTrue(id.equals(parsedId));
        }
    }

    @Test
    @Transactional
    void expiredTokenTest(){
        // Cria um DTO de usuário para teste.
        UsuarioCadastroDTO userDTO = new UsuarioCadastroDTO(
			"user",
			"user@email.com", 
			"user", 
			"user", 
			"M", 
			LocalDate.now(), 
			"user"
		);
        // Cadastrar o usuário através do service.
        usuarioService.cadastrar(userDTO);

        // Cria o grupo na base de dados
        GrupoCreateDTO groupDTO = new GrupoCreateDTO(userDTO.getUsername(), "Group", 2, null);
        grupoService.criar(groupDTO);

        List<Grupo> databaseGroups = grupoRepository.findAll();
        assertFalse(databaseGroups.isEmpty());

        Integer id;
        for(Grupo g : databaseGroups){
            id = g.getId();
            String token = grupoService.gerarConvite(id, Long.valueOf(10));

            assertThrows(TokenFailedException.class, () -> {
                Integer parsedId = (int) grupoService.aceitarConvite(token);
            });
        }
    }
}
