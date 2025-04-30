package com.dauphin.dauphin.exceptions;

public class PermissionDeniedException extends RuntimeException{
    public static final String DENIED_MESSAGE = "Permissão negada";
    public static final String GROUP_DENIED_MESSAGE = "Usuário não tem permissão para realizar essa ação no grupo.";
    public static final String FRIENDSHIP_DENIED_MESSAGE = "Usuário não tem permissão para criar amizade entre dois usuários";
    public static final String CANT_CREATE_GROUP_FOR_OTHERS = "Você não pode criar um grupo para outro usuário.";
    public static final String CANT_CREATE_FRIENDSHIP_FOR_OTHERS = "Você não pode criar uma amizade para outro usuário";
    public static final String CANT_REMOVE_USER_FROM_GROUP = "Você não tem permissão para remover usuários.";
    public static final String CANT_DELETE_GROUP = "Você não tem permissão para apagar o grupo.";
    public static final String CANT_CHANGE_GROUP_ROLES = "Você não pode editar as permissões desse usuário.";

    public PermissionDeniedException(){
        super(DENIED_MESSAGE);
    }

    public PermissionDeniedException(String message){
        super(message);
    }
}
