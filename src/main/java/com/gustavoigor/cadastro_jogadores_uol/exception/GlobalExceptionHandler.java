package com.gustavoigor.cadastro_jogadores_uol.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> handleIllegal(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity
                .internalServerError()
                .body(ex.getMessage());
    }
}
