package com.dauphin.dauphin.Services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dauphin.dauphin.dtos.UsuarioCadastroDTO;
import com.dauphin.dauphin.exceptions.EntityConflictInDatabaseException;
import com.dauphin.dauphin.models.TemAmizade;
import com.dauphin.dauphin.models.TemAmizade.TemAmizadeId;
import com.dauphin.dauphin.repositories.TemAmizadeRepository;
import com.dauphin.dauphin.services.TemAmizadeService;
import com.dauphin.dauphin.services.UsuarioService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TemAmizadeServiceTest {
    @Autowired
    TemAmizadeService temAmizadeService;
    
    @Autowired
    TemAmizadeRepository temAmizadeRepository;

    @Autowired
    UsuarioService usuarioService;

    @BeforeEach
    @Transactional
    void createUsers(){
        UsuarioCadastroDTO dto1 = new UsuarioCadastroDTO(
            "friend1", 
            "friend1@email", 
            "User 1", 
            "user1", 
            "M", 
            LocalDate.now(), 
            "user1_photo"
        );

        try{
            usuarioService.cadastrar(dto1);
        }
        catch(EntityConflictInDatabaseException e){}

        // Cria o DTO de cadastro do segundo usuário.
        UsuarioCadastroDTO dto2 = new UsuarioCadastroDTO(
            "friend2", 
            "friend2@email", 
            "User 2", 
            "user2", 
            "F", 
            LocalDate.now(), 
            "user2_photo"
        );

        try{
            usuarioService.cadastrar(dto2);
        }
        catch(EntityConflictInDatabaseException e){}

        // Cria o DTO de cadastro do segundo usuário.
        UsuarioCadastroDTO dto3 = new UsuarioCadastroDTO(
            "friend3", 
            "friend3@email", 
            "User 3", 
            "user3", 
            "F", 
            LocalDate.now(), 
            "user3_photo"
        );

        try{
            usuarioService.cadastrar(dto3);
        }
        catch(EntityConflictInDatabaseException e){}
    }

    @Test
    @Transactional
    void createFriendshipTest(){
        // Cria o DTO do cadastro do primeiro usuário.
        UsuarioCadastroDTO dto1 = new UsuarioCadastroDTO(
            "user1", 
            "friend1@email", 
            "User 1", 
            "user1", 
            "M", 
            LocalDate.now(), 
            "user1_photo"
        );
        
        // Salva o usuário 1 na base de dados.
        usuarioService.cadastrar(dto1);

        // Cria o DTO de cadastro do segundo usuário.
        UsuarioCadastroDTO dto2 = new UsuarioCadastroDTO(
            "user2", 
            "friend2@email", 
            "User 2", 
            "user2", 
            "F", 
            LocalDate.now(), 
            "user2_photo"
        );

        // Salva o usuário 2 na base de dados.
        usuarioService.cadastrar(dto2);

        // Cria uma amizade entre os usuários.
        temAmizadeService.criar(dto1.getUsername(), dto2.getUsername());

        TemAmizadeId id = new TemAmizadeId(dto1.getUsername(), dto2.getUsername());
        Optional<TemAmizade> amizade = temAmizadeRepository.findById(id);

        assertTrue(amizade.isPresent());
        assertEquals(id, amizade.get().getId());
    }

    @Test
    @Transactional
    void findAllFriendsTest(){
        
        temAmizadeService.criar("friend1", "friend2");
        temAmizadeService.criar("friend1", "friend3");

        List<TemAmizade> amizades = temAmizadeService.listar("friend1");
        assertEquals(amizades.size(), 2);
        assertDoesNotThrow(() -> {
            temAmizadeService.buscar("friend1", "friend2");
            temAmizadeService.buscar("friend1", "friend3");
        });
        

    }

    @Test
    @Transactional
    void deleteFriendshipTest(){
        // Cria o DTO do cadastro do primeiro usuário.
        UsuarioCadastroDTO dto1 = new UsuarioCadastroDTO(
            "user1", 
            "user1@email", 
            "User 1", 
            "user1", 
            "M", 
            LocalDate.now(), 
            "user1_photo"
        );
        
        // Salva o usuário 1 na base de dados.
        usuarioService.cadastrar(dto1);

        // Cria o DTO de cadastro do segundo usuário.
        UsuarioCadastroDTO dto2 = new UsuarioCadastroDTO(
            "user2", 
            "user2@email", 
            "User 2", 
            "user2", 
            "F", 
            LocalDate.now(), 
            "user2_photo"
        );

        // Salva o usuário 2 na base de dados.
        usuarioService.cadastrar(dto2);

        // Cria uma amizade entre os usuários.
        temAmizadeService.criar(dto1.getUsername(), dto2.getUsername());
        TemAmizadeId id = new TemAmizadeId(dto1.getUsername(), dto2.getUsername());
        Optional<TemAmizade> amizade = temAmizadeRepository.findById(id);

        assertTrue(amizade.isPresent());
        assertEquals(id, amizade.get().getId());

        temAmizadeService.apagar(dto1.getUsername(), dto2.getUsername());

        amizade = temAmizadeRepository.findById(id);
        assertFalse(amizade.isPresent());
    }
}
