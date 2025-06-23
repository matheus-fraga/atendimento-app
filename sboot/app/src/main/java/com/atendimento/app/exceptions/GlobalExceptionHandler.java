package com.atendimento.app.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador global de exceções para toda a aplicação.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manipula exceções de validação de argumentos.
     *
     * @param ex Exceção de validação.
     * @return Detalhes do erro de validação.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = new HashMap<>();
        body.put("erro", "Erro de validação");
        body.put("detalhes", fieldErrors);

        return ResponseEntity.badRequest().body(body);
    }


    /**
     * Manipula exceções de validação de constraints (ex.: validação de CPF).
     *
     * @param ex Exceção de validação.
     * @return Detalhes do erro de validação.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> 
            errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        errors.put("error", "Erro de validação");
        errors.put("timestamp", LocalDateTime.now());
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Manipula exceções genéricas não tratadas.
     *
     * @param ex Exceção genérica.
     * @return Detalhes do erro.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Erro interno no servidor");
        error.put("message", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}