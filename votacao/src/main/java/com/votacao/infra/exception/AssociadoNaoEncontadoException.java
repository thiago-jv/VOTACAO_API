package com.votacao.infra.exception;

public class AssociadoNaoEncontadoException extends EntidadeNaoEncontradaException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public AssociadoNaoEncontadoException(String messagem) {
		super(messagem);
	}

	public AssociadoNaoEncontadoException(Long id) {
		this(String.format("Não existe cadastro de associado com o código %d", id));
	}

}
