package br.ufc.mobile.vendasfacil.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.ufc.mobile.vendasfacil.exception.InvalidJwtAuthenticationException;
import br.ufc.mobile.vendasfacil.exception.NotAllowedException;
import br.ufc.mobile.vendasfacil.exception.NotFoundException;
import br.ufc.mobile.vendasfacil.exception.VendasFacilException;
import br.ufc.mobile.vendasfacil.model.ExceptionResponse;

@RestControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(NotFoundException ex) {
        ExceptionResponse response = ExceptionResponse.ExceptionResponseBuilder.anExceptionResponseBuilder()
                .withStatus(HttpStatus.NOT_FOUND)
                .withError(HttpStatus.NOT_FOUND.name())
                .withMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }
	
	@ExceptionHandler({VendasFacilException.class, EntityNotFoundException.class})
	public ResponseEntity<ExceptionResponse> handleBadRequest(Exception ex) {
        ExceptionResponse response = ExceptionResponse.ExceptionResponseBuilder.anExceptionResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withError(HttpStatus.BAD_REQUEST.name())
                .withMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }
	
	@ExceptionHandler({InvalidJwtAuthenticationException.class})
	public ResponseEntity<ExceptionResponse> handleForbiddenException(InvalidJwtAuthenticationException ex) {
        ExceptionResponse response = ExceptionResponse.ExceptionResponseBuilder.anExceptionResponseBuilder()
                .withStatus(HttpStatus.FORBIDDEN)
                .withError(HttpStatus.FORBIDDEN.name())
                .withMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }
	
	@ExceptionHandler({NotAllowedException.class})
	public ResponseEntity<ExceptionResponse> handleNotAllowedException(NotAllowedException ex) {
        ExceptionResponse response = ExceptionResponse.ExceptionResponseBuilder.anExceptionResponseBuilder()
                .withStatus(HttpStatus.UNAUTHORIZED)
                .withError(HttpStatus.UNAUTHORIZED.name())
                .withMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }
}
