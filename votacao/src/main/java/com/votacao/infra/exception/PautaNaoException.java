package com.votacao.infra.exception;

public class PautaNaoException extends EntidadeNaoEncontradaException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PautaNaoException(String messagem) {
		super(messagem);
	}

	public PautaNaoException(Long id) {
		this(String.format("Pauta já encontra-se com status NÃO com código %d", id));
	}

}
