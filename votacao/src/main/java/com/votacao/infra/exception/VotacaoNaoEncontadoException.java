package com.votacao.infra.exception;

public class VotacaoNaoEncontadoException extends EntidadeNaoEncontradaException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public VotacaoNaoEncontadoException(String messagem) {
		super(messagem);
	}

	public VotacaoNaoEncontadoException(Long id) {
		this(String.format("Não existe cadastro de votação com o código %d", id));
	}

}
