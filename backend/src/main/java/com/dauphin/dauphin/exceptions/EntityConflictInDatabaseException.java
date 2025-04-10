package com.dauphin.dauphin.exceptions;

public class EntityConflictInDatabaseException extends RuntimeException{
    public EntityConflictInDatabaseException(String message){
        super(message);
    }
    
    public EntityConflictInDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityConflictInDatabaseException(Throwable cause) {
        super(cause);
    }
}
