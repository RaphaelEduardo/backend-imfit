package br.com.imfit.exceptions.handlers;

import br.com.imfit.exceptions.UsuarioNotFoundException;
import br.com.imfit.exceptions.error.responses.UsuarioErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UsuarioExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UsuarioErrorResponse> handleException(UsuarioNotFoundException exc) {

        UsuarioErrorResponse error = new UsuarioErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
