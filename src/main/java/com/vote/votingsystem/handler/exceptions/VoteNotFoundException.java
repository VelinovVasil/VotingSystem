package com.vote.votingsystem.handler.exceptions;

public class VoteNotFoundException extends RuntimeException{
    public VoteNotFoundException(String message) {
        super(message);
    }
}
