package br.ufc.mobile.vendasfacil.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public InvalidJwtAuthenticationException(String message) {
        super(message);
    }

}
