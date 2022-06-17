package com.javamentor.qa.platform.webapp.controllers.exceptions;

public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException(String message) {
        super(message);

    }
}
