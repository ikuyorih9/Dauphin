package com.dauphin.dauphin.exceptions;

public class EntityNotFoundInDatabaseException extends RuntimeException{
    public final static String NOT_FOUND_MESSAGE = "Entidade não está cadastrado no sistema.";
    public final static String USER_NOT_FOUND_MESSAGE = "Usuário não está cadastrado no sistema."; 
    public final static String FRIENDSHIP_NOT_FOUND_MESSAGE = "Não há registro dessa amizade no sistema.";
    public final static String GROUP_NOT_FOUND_MESSAGE = "Não há grupo com esse ID.";
    public final static String NOT_MEMBER_OF_GROUP_MESSAGE = "Usuário não é membro do grupo.";
    public EntityNotFoundInDatabaseException(){
        super(NOT_FOUND_MESSAGE);
    }
    public EntityNotFoundInDatabaseException(String message){
        super(message);
    }
}
