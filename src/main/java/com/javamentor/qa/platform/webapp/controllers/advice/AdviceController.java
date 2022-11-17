package com.javamentor.qa.platform.webapp.controllers.advice;

import com.javamentor.qa.platform.exception.ConstrainException;
import com.javamentor.qa.platform.exception.NoSuchDaoException;
import com.javamentor.qa.platform.exception.PageException;
import com.javamentor.qa.platform.webapp.controllers.exceptions.AddBookmarkException;
import com.javamentor.qa.platform.webapp.controllers.exceptions.AuthUserNotAuthorCreateGroupChatException;
import com.javamentor.qa.platform.webapp.controllers.exceptions.DeleteGlobalChatException;
import com.javamentor.qa.platform.webapp.controllers.exceptions.IsEmptyUserIdsException;
import com.javamentor.qa.platform.webapp.controllers.exceptions.UserRemovedFromTheSingleChat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchDaoException.class)
    public ResponseEntity<String> handleNoSuchDaoException(NoSuchDaoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ConstrainException.class)
    public ResponseEntity<String> handleConstrainException(ConstrainException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> handleTransactionSystemException(TransactionSystemException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AddBookmarkException.class)
    public ResponseEntity<String> handleAddBookmarkException(AddBookmarkException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IsEmptyUserIdsException.class)
    public ResponseEntity<String> handleIsEmptyUserIdsException(IsEmptyUserIdsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UserRemovedFromTheSingleChat.class)
    public ResponseEntity<String> handleUserRemovedFromTheSingleChat(UserRemovedFromTheSingleChat e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(PageException.class)
    public ResponseEntity<String> handlePageException(PageException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AuthUserNotAuthorCreateGroupChatException.class)
    public ResponseEntity<String> handleAuthUserNotAuthorCreateGroupChatException(AuthUserNotAuthorCreateGroupChatException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DeleteGlobalChatException.class)
    public ResponseEntity<String> handleDeleteGlobalChatException(DeleteGlobalChatException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
