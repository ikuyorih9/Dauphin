package com.dauphin.dauphin.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.dauphin.dauphin.dtos.UsuarioCadastroDTO;
import com.dauphin.dauphin.exceptions.EntityConflictInDatabaseException;
import com.dauphin.dauphin.exceptions.EntityNotFoundInDatabaseException;
import com.dauphin.dauphin.models.Usuario;
import com.dauphin.dauphin.repositories.UsuarioRepository;
import com.dauphin.dauphin.services.UsuarioService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class UsuarioServiceTest {
    @Autowired
	private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

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
        System.out.println(usuario.get().toString());

        // Testa se um objeto Usuario foi encontrado e se o seu username é o mesmo do DTO.
        assertTrue(usuario.isPresent());
        assertEquals(dto.getUsername(), usuario.get().getUsername());
	}

    @Test
    @Transactional
    void signupConflictTest(){ // Testa o conflito de entidades na base de dados.
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

    @Test
    @Transactional
    @Rollback(false)
    void deleteAllUsers(){
        List<Usuario> users = usuarioService.lista();
        for(Usuario u : users){
            System.out.println("Deleting " +  u.getUsername() + "...");
            usuarioService.deletar(u.getUsername());
            assertThrows(EntityNotFoundInDatabaseException.class, ()->{
                usuarioService.buscar(u.getUsername());
            });
        }
    }
}
