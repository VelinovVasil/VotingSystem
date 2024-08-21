package com.vote.votingsystem.handler.exceptions;

public class NoIdentityFoundException extends RuntimeException{
    public NoIdentityFoundException(String message) {
        super(message);
    }
}
