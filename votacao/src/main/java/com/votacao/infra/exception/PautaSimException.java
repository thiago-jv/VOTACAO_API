package com.votacao.infra.exception;

public class PautaSimException extends EntidadeNaoEncontradaException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PautaSimException(String messagem) {
		super(messagem);
	}

	public PautaSimException(Long id) {
		this(String.format("Pauta já encontra-se com status SIM com código %d", id));
	}

}
