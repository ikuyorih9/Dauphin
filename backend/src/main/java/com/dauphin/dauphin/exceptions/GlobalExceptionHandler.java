package com.dauphin.dauphin.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityConflictInDatabaseException.class)
    public ResponseEntity<Object> handleEntityConflictDatabaseException(EntityConflictInDatabaseException e){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundInDatabaseException.class)
    public ResponseEntity<String> handleEntityNotFoundInDatabaseException(EntityNotFoundInDatabaseException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
