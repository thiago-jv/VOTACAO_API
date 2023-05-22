package com.votacao.infra.exception;

public class PautaNaoEncontadoException extends EntidadeNaoEncontradaException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PautaNaoEncontadoException(String messagem) {
		super(messagem);
	}

	public PautaNaoEncontadoException(Long id) {
		this(String.format("Não existe cadastro de pauta com o código %d", id));
	}

}
