package com.dauphin.dauphin.exceptions;

public class EntityNotFoundInDatabaseException extends RuntimeException{
    public EntityNotFoundInDatabaseException(String message){
        super(message);
    }
}
