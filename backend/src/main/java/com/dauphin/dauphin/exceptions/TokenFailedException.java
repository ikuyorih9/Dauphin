package com.dauphin.dauphin.exceptions;

public class TokenFailedException extends RuntimeException{
    public static final String TOKEN_EXPIRED = "O convite para o grupo expirou.";
    public static final String TOKEN_INVALID = "O convite para o grupo é invalido.";
    public static final String GENERIC_TOKEN_ERROR = "Erro ao aceitar o token.";

    public TokenFailedException(){
        super(GENERIC_TOKEN_ERROR);
    }

    public TokenFailedException(String message){
        super(message);
    }
}
