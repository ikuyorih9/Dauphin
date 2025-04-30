package com.dauphin.dauphin.services;

import java.util.List;

import org.springframework.data.repository.query.parser.Part;
import org.springframework.stereotype.Service;

import com.dauphin.dauphin.component.AuthService;
import com.dauphin.dauphin.constants.GrupoRolesConsts;
import com.dauphin.dauphin.dtos.GroupNewUserDTO;
import com.dauphin.dauphin.dtos.GrupoCreateDTO;
import com.dauphin.dauphin.exceptions.EntityNotFoundInDatabaseException;
import com.dauphin.dauphin.exceptions.PermissionDeniedException;
import com.dauphin.dauphin.models.Grupo;
import com.dauphin.dauphin.models.ParticipaGrupo;
import com.dauphin.dauphin.models.ParticipaGrupo.ParticipaGrupoId;
import com.dauphin.dauphin.models.RoleGrupo;
import com.dauphin.dauphin.models.Usuario;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class GrupoParticipacaoService {
    private GrupoService grupoService;
    private ParticipaGrupoService participaGrupoService;
    private AuthService authService;

    public GrupoParticipacaoService(GrupoService grupoService, ParticipaGrupoService participaGrupoService, AuthService authService) {
        this.grupoService = grupoService;
        this.participaGrupoService = participaGrupoService;
        this.authService = authService;
    }

    @Transactional
    public Grupo criar(GrupoCreateDTO groupDTO){
        String currentUser = authService.getCurrentUsername();
        groupDTO.setUsername(currentUser);
        Grupo created = grupoService.criar(groupDTO);
        participaGrupoService.criar(new GroupNewUserDTO(currentUser, created.getId(), "HOST"));
        return created;
    }

    @Transactional
    public void apagar(Integer id){
        String currentUser = authService.getCurrentUsername();
        ParticipaGrupo participacao = participaGrupoService.buscar(new ParticipaGrupoId(currentUser, id));
        if (participacao.getRole().getPodeApagar()) grupoService.apagar(id);
        else throw new PermissionDeniedException(PermissionDeniedException.CANT_DELETE_GROUP);
    }

    public String gerarConvite(Integer id){
        boolean canInvite = participaGrupoService.membroTemPermissao(
            new ParticipaGrupoId(
                authService.getCurrentUsername(), 
                id
            ), 
            GrupoRolesConsts.INVITE
        );

        if(!canInvite) throw new PermissionDeniedException(PermissionDeniedException.GROUP_DENIED_MESSAGE);

        return grupoService.gerarConvite(id, GrupoService.EXPIRATION_TIME);
    }

    public void aceitarConvite(String token){
        Long groupId = grupoService.aceitarConvite(token);
        participaGrupoService.criar(new GroupNewUserDTO(authService.getCurrentUsername(), groupId.intValue(), GrupoRolesConsts.MEMBER));
    }

    public void alterarRole(Integer id, String username, String newRole){
        // Procura participação de usuário autenticado no grupo.
        ParticipaGrupo p = participaGrupoService.buscar(new ParticipaGrupoId(authService.getCurrentUsername(), id));

        //Verifica se o usuário NÃO pode editar o grupo.
        if(!p.getRole().getPodeEditar()) throw new PermissionDeniedException(PermissionDeniedException.CANT_CHANGE_GROUP_ROLES); // Lança exceção de permissão negada.

        // Altera as permissões do usuário.
        participaGrupoService.alterarRole(id, username, newRole);
    }

}
