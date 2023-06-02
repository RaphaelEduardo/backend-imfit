package br.com.imfit.exceptions.handlers;

import br.com.imfit.exceptions.TreinoNotFoundException;
import br.com.imfit.exceptions.error.responses.TreinoErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TreinoExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<TreinoErrorResponse> handleException(TreinoNotFoundException exc) {

        TreinoErrorResponse error = new TreinoErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
