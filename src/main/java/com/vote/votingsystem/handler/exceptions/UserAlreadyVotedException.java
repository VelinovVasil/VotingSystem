package com.vote.votingsystem.handler.exceptions;

public class UserAlreadyVotedException extends RuntimeException {

    public UserAlreadyVotedException(String message) {
        super(message);
    }
}
