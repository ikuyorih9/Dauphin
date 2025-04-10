package com.dauphin.dauphin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Commit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.dauphin.dauphin.dtos.UsuarioCadastroDTO;
import com.dauphin.dauphin.exceptions.EntityConflictInDatabaseException;
import com.dauphin.dauphin.services.UsuarioService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import com.dauphin.dauphin.models.Usuario;
import com.dauphin.dauphin.repositories.UsuarioRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {
    @Autowired
	private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private EntityManager entityManager;
	
	@Test
    @Transactional
	void signupServiceTest(){ // Testa o cadastro de um usuário através do UsuarioService.
        // Cria um DTO de usuário para teste.
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO(
			"user",
			"user@email.com", 
			"user", 
			"user", 
			"M", 
			LocalDate.now(), 
			"user"
		);

        // Cadastrar o usuário através do service.
        usuarioService.cadastrar(dto);

        // Busca o usuário na base de dados.
        Optional<Usuario> usuario = usuarioRepository.findByUsername(dto.getUsername());

        // Testa se um objeto Usuario foi encontrado e se o seu username é o mesmo do DTO.
        assertTrue(usuario.isPresent());
        assertEquals(dto.getUsername(), usuario.get().getUsername());
	}

	@Test
    @Transactional
    // PROBLEMA: ESTÁ PERSISTINDO O USUÁRIO NA BASE DE DADOS.
	void signupWithHTTPRequestsTest(){ // Testa o cadastro de um usuário através de uma requisição HTTP.
        // Cria um DTO de usuário para teste.
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO(
			"user",
			"user@email.com", 
			"user", 
			"user", 
			"M", 
			LocalDate.now(), 
			"user"
		);
        
        // Cria o cabeçalho da requisição HTTP.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Cria a entidade da requisição HTTP, com o DTO como corpo JSON.
        HttpEntity<UsuarioCadastroDTO> request = new HttpEntity<>(dto,headers);

        // Faz a requisição POST para auth/signup.
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/signup", request, String.class);

        // Verifica se o status da resposta da requisição foi CREATED.
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Transactional
    void signupConflictTest(){
        // Cria um DTO de usuário para teste.
        UsuarioCadastroDTO dto1 = new UsuarioCadastroDTO(
			"user",
			"user@email.com", 
			"user", 
			"user", 
			"M", 
			LocalDate.now(), 
			"user"
		);

        // Cadastrar o usuário através do service.
        usuarioService.cadastrar(dto1);

        // Cria um outro DTO, com mesmo nome de usuário.
        UsuarioCadastroDTO dto2 = new UsuarioCadastroDTO(
			"user",
			"anotheruser@email.com", 
			"another user", 
			"another_user", 
			"M", 
			LocalDate.now(), 
			"user"
		);

        // Verifica se o exception EntityConflictInDatabaseException é lanãdo ao tentar cadastrar um usuário com mesmo username. 
        assertThrows(EntityConflictInDatabaseException.class, () -> {
            usuarioService.cadastrar(dto2);
        });
    }

    @AfterEach
    void limparBanco() {
        Optional<Usuario> usuarioAntes = usuarioRepository.findByUsername("user");
        System.out.println("Usuário encontrado antes do delete? " + usuarioAntes.isPresent());

        usuarioRepository.deleteByUsername("user");

        Optional<Usuario> usuarioDepois = usuarioRepository.findByUsername("user");
        System.out.println("Usuário encontrado depois do delete? " + usuarioDepois.isPresent());
    }
}
