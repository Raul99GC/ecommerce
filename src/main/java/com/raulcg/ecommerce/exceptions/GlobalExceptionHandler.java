package com.raulcg.ecommerce.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ================== Manejo de Excepciones Personalizadas ==================

    // Manejo de EmailAlreadyExistsException
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Manejo de UsernameAlreadyExistsException
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // ================== Manejo de Excepciones de Validación de Tokens ==================

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Map<String, Object>> handleMalformedJwtException(MalformedJwtException ex) {
        return buildResponseEntity("Invalid JWT token", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(ExpiredJwtException ex) {
        return buildResponseEntity("Expired JWT token", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return buildResponseEntity("Unsupported JWT token", HttpStatus.BAD_REQUEST);
    }

    // ================== Manejo de Errores Generales ==================

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // ================== Método Genérico para Construir la Respuesta ==================

    private ResponseEntity<Map<String, Object>> buildResponseEntity(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
