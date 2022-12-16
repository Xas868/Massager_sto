package com.javamentor.qa.platform.webapp.controllers.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String s) {
        super(s);
    }
}
