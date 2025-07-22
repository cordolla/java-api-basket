package com.basket.api.exception;

public class TeamAlreadyExistsException extends BusinessRuleException {

    public TeamAlreadyExistsException(String message) {
        super(message);
    }
}
