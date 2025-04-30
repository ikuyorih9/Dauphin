package com.dauphin.dauphin.Controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import com.dauphin.dauphin.constants.GrupoRolesConsts;
import com.dauphin.dauphin.dtos.GroupNewUserDTO;
import com.dauphin.dauphin.dtos.GrupoCreateDTO;
import com.dauphin.dauphin.dtos.UsuarioCadastroDTO;
import com.dauphin.dauphin.exceptions.EntityConflictInDatabaseException;
import com.dauphin.dauphin.exceptions.EntityNotFoundInDatabaseException;
import com.dauphin.dauphin.models.Grupo;
import com.dauphin.dauphin.models.ParticipaGrupo;
import com.dauphin.dauphin.models.TemAmizade;
import com.dauphin.dauphin.models.TemAmizade.TemAmizadeId;
import com.dauphin.dauphin.models.Usuario;
import com.dauphin.dauphin.repositories.GrupoRepository;
import com.dauphin.dauphin.repositories.UsuarioRepository;
import com.dauphin.dauphin.services.GrupoParticipacaoService;
import com.dauphin.dauphin.services.GrupoService;
import com.dauphin.dauphin.services.ParticipaGrupoService;
import com.dauphin.dauphin.services.TemAmizadeService;
import com.dauphin.dauphin.services.UsuarioService;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTest {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TemAmizadeService temAmizadeService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private ParticipaGrupoService participaGrupoService;
    @Autowired
    private GrupoParticipacaoService grupoParticipacaoService;

    private Usuario user;
    private Grupo grupo;

    @BeforeEach
    void setupAuthentication() {
        // Cria uma autenticação fictícia (mockada) com nome "user"
        var auth = new UsernamePasswordAuthenticationToken("user", null, List.of());
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }

    @BeforeEach
    void createUsers(){
        // Salva o usuário 1 na base de dados.
        try{
            user = usuarioService.cadastrar(new UsuarioCadastroDTO(
                "user", 
                "user", 
                "user", 
                "user", 
                "M", 
                LocalDate.now(), 
                "user_photo"
            ));
            usuarioRepository.flush();
        }
        catch(EntityConflictInDatabaseException e){
            System.out.println("[ENTITY CONFLCIT IGNORING]: usuário já está cadastrado!");
        }

        grupo = grupoParticipacaoService.criar(new GrupoCreateDTO(
            user.getUsername(), 
            "Group", 
            2, 
            null
        ));
    }

    @AfterEach
    void deleteUsers(){
        usuarioRepository.deleteAll();
        grupoRepository.deleteAll();
    }

    @Test
    void createFriendshipWithHTTPRequisitionTest(){
        // Cria o DTO do cadastro do primeiro usuário.
        UsuarioCadastroDTO dto1 = new UsuarioCadastroDTO(
            "user", 
            "user@email", 
            "User 1", 
            "user1", 
            "M", 
            LocalDate.now(), 
            "user1_photo"
        );
        try{
            // Salva o usuário 1 na base de dados.
            usuarioService.cadastrar(dto1);
            usuarioRepository.flush();
        }
        catch(EntityConflictInDatabaseException e){}

        // Cria o DTO de cadastro do segundo usuário.
        UsuarioCadastroDTO dto2 = new UsuarioCadastroDTO(
            "to", 
            "to@email", 
            "User 2", 
            "user2", 
            "F", 
            LocalDate.now(), 
            "user2_photo"
        );

        try{
            // Salva o usuário 2 na base de dados.
            usuarioService.cadastrar(dto2);
            usuarioRepository.flush();
        }
        catch(EntityConflictInDatabaseException e){

        }

        // Cria o cabeçalho da requisição HTTP.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user" + ":" + "password").getBytes()));

        // Cria a entidade da requisição HTTP, com o DTO como corpo JSON.
        HttpEntity<String> request = new HttpEntity<>(dto2.getUsername(), headers);

        // Faz a requisição POST para auth/signup.
        ResponseEntity<String> response = restTemplate.postForEntity("/users/friends", request, String.class);
        // Verifica se o status da resposta da requisição foi CREATED.
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertDoesNotThrow(()->{
            temAmizadeService.buscar("user", "to");
        });
    }   

    @Test
    void deleteFriendshipWithHttpRequestTest(){
        String username1 = "user";
        String username2 = "to";
    
        // Cria o cabeçalho da requisição HTTP.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user" + ":" + "password").getBytes()));

        // Cria a entidade da requisição HTTP, com o DTO como corpo JSON.
        HttpEntity<String> request = new HttpEntity<>(username2, headers);

        // Execute a chamada DELETE com corpo
        ResponseEntity<String> response = restTemplate.exchange(
            "/users/friends",
            HttpMethod.DELETE,
            request,
            String.class
        );

        System.out.println("STATUS: " + response.getStatusCode() + " : " + response.getBody());
        // Verifica se o status da resposta da requisição foi CREATED.
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertThrows(EntityNotFoundInDatabaseException.class, () -> {
            temAmizadeService.buscar(username1, username2);
        });

    }


    @Test
    void createGroupWithHttpRequestTest(){        
        // Cria o cabeçalho da requisição HTTP.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user" + ":" + "password").getBytes()));

        // Cria a entidade da requisição HTTP, com o DTO como corpo JSON.
        HttpEntity<GrupoCreateDTO> request = new HttpEntity<>(new GrupoCreateDTO(user.getUsername(), "Grupo", 5, "PUBLICO"), headers);

        // Faz a requisição POST para auth/signup.
        ResponseEntity<String> response = restTemplate.postForEntity("/users/group", request, String.class);
        System.out.println("STATUS: " + response.getStatusCode() + " : " + response.getBody());
        
        // Verifica se o status da resposta da requisição foi CREATED.
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test 
    void generateGroupInviteWithHttpRequestTest(){
        // Cria o cabeçalho da requisição HTTP.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user" + ":" + "password").getBytes()));
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/users/group/{id}/invite", HttpMethod.GET, request, String.class, grupo.getId());

        // Verifica se o status da resposta da requisição foi CREATED.
        assertEquals(HttpStatus.OK, response.getStatusCode());

        String token = response.getBody();
        System.out.println("[TOKEN]: " + token);
        assertNotNull(response.getBody());
    }

    @Test
    void acceptGroupInviteWithHttpRequestTest(){
        // Cria o cabeçalho da requisição HTTP.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user" + ":" + "password").getBytes()));
        
        String token = grupoService.gerarConvite(grupo.getId(), GrupoService.EXPIRATION_TIME);
        HttpEntity<String> request = new HttpEntity<>(token, headers);
        System.out.println("[TOKEN]: " + token);
        

        ResponseEntity<String> response = restTemplate.postForEntity("/users/group/join", request, String.class);
        // System.out.println("[TOKEN]: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<ParticipaGrupo> participantes = participaGrupoService.buscaParticipantes(grupo.getId());
        boolean estaNaLista = false;
        boolean temRoleMembro = false;
        for (ParticipaGrupo p : participantes){
            if(p.getUsuario().getUsername().equals("user"))
                estaNaLista = true;
            if(p.getRole().getNome().equals(GrupoRolesConsts.MEMBER))
                temRoleMembro = true;
        }
        assertTrue(estaNaLista);
        assertTrue(temRoleMembro);
    }

    // Deleta todos os grupos através da requisição HTTP (é preciso ter algum grupo na base de dados)
    @Test
    void deleteGroupAndUsersWithHttpRequestTest(){
        // Cria o cabeçalho da requisição HTTP.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user" + ":" + "password").getBytes()));
        
        
        List<Grupo> databaseGroups = grupoRepository.findAll();
        for(Grupo g : databaseGroups){
            Integer id = g.getId();
            // Cria a entidade da requisição HTTP, com o DTO como corpo JSON.
            HttpEntity<Integer> request = new HttpEntity<>(id, headers);
            
            // Execute a chamada DELETE com corpo
            ResponseEntity<String> response = restTemplate.exchange(
                "/users/group/delete",
                HttpMethod.DELETE,
                request,
                String.class
            );
            System.out.println("STATUS: " + response.getStatusCode() + " : " + response.getBody());

            // Verifica se o status da resposta da requisição foi CREATED.
            assertEquals(HttpStatus.OK, response.getStatusCode());

            assertThrows(EntityNotFoundInDatabaseException.class, () -> {
                grupoService.buscar(id);
            });
        }
    }


}
