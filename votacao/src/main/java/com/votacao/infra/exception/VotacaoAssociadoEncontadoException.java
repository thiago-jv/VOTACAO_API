package com.votacao.infra.exception;

public class VotacaoAssociadoEncontadoException extends EntidadeNaoEncontradaException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public VotacaoAssociadoEncontadoException(String messagem) {
		super(messagem);
	}

	public VotacaoAssociadoEncontadoException(Long id) {
		this(String.format("Já existe lancamento de voto para o associado %d", id));
	}

}
