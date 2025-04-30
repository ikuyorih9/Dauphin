package com.dauphin.dauphin.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.dauphin.dauphin.constants.GrupoRolesConsts;
import com.dauphin.dauphin.dtos.GroupNewUserDTO;
import com.dauphin.dauphin.exceptions.EntityNotFoundInDatabaseException;
import com.dauphin.dauphin.exceptions.PermissionDeniedException;
import com.dauphin.dauphin.models.Grupo;
import com.dauphin.dauphin.models.ParticipaGrupo;
import com.dauphin.dauphin.models.ParticipaGrupo.ParticipaGrupoId;
import com.dauphin.dauphin.models.RoleGrupo;
import com.dauphin.dauphin.models.Usuario;
import com.dauphin.dauphin.repositories.ParticipaGrupoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ParticipaGrupoServiceImplementation implements ParticipaGrupoService{
    private GrupoService grupoService;
    private ParticipaGrupoRepository participaGrupoRepository;
    private RoleGrupoService roleGrupoService;
    private UsuarioService usuarioService;

    public ParticipaGrupoServiceImplementation(GrupoService grupoService,
            ParticipaGrupoRepository participaGrupoRepository, UsuarioService usuarioService, RoleGrupoService roleGrupoService) {
        this.grupoService = grupoService;
        this.participaGrupoRepository = participaGrupoRepository;
        this.usuarioService = usuarioService;
        this.roleGrupoService = roleGrupoService;
    }

    @Override
    public ParticipaGrupo criar(GroupNewUserDTO dto){
        Usuario usuario = usuarioService.buscar(dto.getUsername());
        Grupo grupo = grupoService.buscar(dto.getId());
        RoleGrupo role = roleGrupoService.buscar(dto.getRole());
        ParticipaGrupo participacao = new ParticipaGrupo(usuario, grupo, LocalDate.now(), role);

        return participaGrupoRepository.save(participacao);
    }  

    @Override
    public ParticipaGrupo buscar(ParticipaGrupoId id){
        usuarioService.buscar(id.getUsername());
        grupoService.buscar(id.getId());
        return participaGrupoRepository.findById(id).orElseThrow(() -> new EntityNotFoundInDatabaseException(EntityNotFoundInDatabaseException.NOT_MEMBER_OF_GROUP_MESSAGE));
    }

    @Override
    public void remover(ParticipaGrupoId id, String currentUserUsername){
        if(!membroTemPermissao(new ParticipaGrupoId(currentUserUsername, id.getId()), GrupoRolesConsts.REMOVE)){
            throw new PermissionDeniedException(PermissionDeniedException.CANT_REMOVE_USER_FROM_GROUP);
        }
        participaGrupoRepository.deleteById(id);
    }

    @Override
    public void alterarRole(Integer id, String username, String newRoleName){
        ParticipaGrupo participacao = buscar(new ParticipaGrupoId(username, id));
        participacao.setRole(roleGrupoService.buscar(newRoleName));
        participaGrupoRepository.save(participacao);
    }

    @Override
    public List<ParticipaGrupo> buscaParticipantes(Integer id){
        return participaGrupoRepository.findByGroup(id);
    }

    @Override
    public RoleGrupo buscaRole(ParticipaGrupoId id) {
        return participaGrupoRepository.findRoleById(id).orElseThrow(() -> new EntityNotFoundException("Role da participação não foi encontrada."));
    }

    @Override
    public boolean membroTemPermissao(ParticipaGrupoId id, String permissao){
        RoleGrupo role = buscar(id).getRole();
        
        if (List.of(GrupoRolesConsts.PERMISSIONS).contains(permissao)) {
            if(permissao.equals(GrupoRolesConsts.INVITE) && role.getPodeConvidar())
                return true;

            if(permissao.equals(GrupoRolesConsts.EDIT) && role.getPodeEditar())
                return true;
            
            if(permissao.equals(GrupoRolesConsts.REMOVE) && role.getPodeRemover())
                return true;
        }
        return false;
    }

    
}
