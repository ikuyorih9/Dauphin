package com.dauphin.dauphin.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dauphin.dauphin.exceptions.EntityNotFoundInDatabaseException;
import com.dauphin.dauphin.models.TemAmizade;
import com.dauphin.dauphin.models.Usuario;
import com.dauphin.dauphin.repositories.TemAmizadeRepository;
import com.dauphin.dauphin.models.TemAmizade.TemAmizadeId;

@Service
public class TemAmizadeServiceImplementation implements TemAmizadeService {

    private UsuarioService usuarioService;
    private TemAmizadeRepository temAmizadeRepository;

    public TemAmizadeServiceImplementation(UsuarioService usuarioService, TemAmizadeRepository temAmizadeRepository) {
        this.usuarioService = usuarioService;
        this.temAmizadeRepository = temAmizadeRepository;
    }

    // Busca uma Amizade pelo seu ID.
    @Override
    public TemAmizade buscar(String username1, String username2){
        return temAmizadeRepository.findById(new TemAmizadeId(username1, username2)).orElseThrow(() -> new EntityNotFoundInDatabaseException(EntityNotFoundInDatabaseException.FRIENDSHIP_NOT_FOUND_MESSAGE));
    }

    // Cria uma amizade através dos usernames dos dois usuários.
    @Override
    public TemAmizade criar(String username1, String username2) {
        Usuario usuario1 = usuarioService.buscar(username1);
        Usuario usuario2 = usuarioService.buscar(username2);

        TemAmizade amizade = new TemAmizade(usuario1, usuario2, LocalDate.now());
        return temAmizadeRepository.save(amizade);
    }

    // Apaga uma amizade através do username de dois usuários.
    @Override
    public void apagar(String username1, String username2){
        TemAmizadeId id = new TemAmizadeId(username1, username2);
        TemAmizade amizade = temAmizadeRepository.findById(id).orElseThrow(() -> new EntityNotFoundInDatabaseException());
        temAmizadeRepository.delete(amizade);
    }

    // Busca amizades do usuários.
    @Override
    public List<TemAmizade> listar(String username){
        return temAmizadeRepository.findByUsername(username);
    }

    

}
