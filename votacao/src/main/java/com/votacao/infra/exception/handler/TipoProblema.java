package com.votacao.infra.exception.handler;

import lombok.Getter;

@Getter
public enum TipoProblema {

	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontra", "Entidade não encontrada"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado");

	private String titulo;
	
	private String uri;
	
	private TipoProblema(String path, String titulo) {
		this.uri = "https://com.votacao.com.br" + path;
		this.titulo = titulo;
	}
}
