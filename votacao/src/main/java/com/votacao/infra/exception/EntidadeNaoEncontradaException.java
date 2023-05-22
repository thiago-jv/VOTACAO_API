package com.votacao.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Essa exception irá ser lançada, quando por exemplo, houver uma tentativa de revemor um registro, porém o mesmo não for encontrado
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String messagem) {
		super(messagem);
	}
}
