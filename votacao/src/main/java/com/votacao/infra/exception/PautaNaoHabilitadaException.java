package com.votacao.infra.exception;

public class PautaNaoHabilitadaException extends EntidadeNaoEncontradaException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PautaNaoHabilitadaException(String messagem) {
		super(messagem);
	}

	public PautaNaoHabilitadaException(Long id) {
		this(String.format("Pauta não habilitada para votação com código %d", id));
	}

}
