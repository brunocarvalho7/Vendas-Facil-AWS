package br.ufc.mobile.vendasfacil.exception;

public class NotAllowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotAllowedException() {
        super("Sem permissão para realizar a requisição desejada");
    }


}
