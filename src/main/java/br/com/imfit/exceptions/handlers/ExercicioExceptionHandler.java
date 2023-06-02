package br.com.imfit.exceptions.handlers;

import br.com.imfit.exceptions.ExercicioNotFoundException;
import br.com.imfit.exceptions.error.responses.ExercicioErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExercicioExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExercicioErrorResponse> handleException(ExercicioNotFoundException exc) {

        ExercicioErrorResponse error = new ExercicioErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
