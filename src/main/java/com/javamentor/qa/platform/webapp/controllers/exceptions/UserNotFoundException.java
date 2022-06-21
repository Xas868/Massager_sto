package com.javamentor.qa.platform.webapp.controllers.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);

    }
}
