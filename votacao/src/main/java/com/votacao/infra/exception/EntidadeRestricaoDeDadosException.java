package com.votacao.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Essa classe é uma exception
 * Irá ser lançada quando houver algum tipo de conflito, por exemplo, remover um arquivo que já é utilizado ou referênciado
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeRestricaoDeDadosException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntidadeRestricaoDeDadosException(String mensagem) {
		super(mensagem);
	}

}
