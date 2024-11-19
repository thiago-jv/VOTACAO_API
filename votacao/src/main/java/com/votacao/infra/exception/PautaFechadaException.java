package com.votacao.infra.exception;

public class PautaFechadaException extends EntidadeNaoEncontradaException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PautaFechadaException(String messagem) {
		super(messagem);
	}

	public PautaFechadaException(Long id) {
		this(String.format("Pauta já FECHADA com código %d", id));
	}

}
