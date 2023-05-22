package com.votacao.infra.exception;

public class VotacaoNaoHabilitadaException extends EntidadeNaoEncontradaException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public VotacaoNaoHabilitadaException(String messagem) {
		super(messagem);
	}

	public VotacaoNaoHabilitadaException(Long id) {
		this(String.format("Pauta não habilitada para votação com o código %d", id));
	}

}
