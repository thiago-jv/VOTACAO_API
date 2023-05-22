package com.votacao.infra.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problema {

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "https://com.votacao/dados-invalidos", position = 3)
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos", position = 2)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos", position = 6)
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos", position = 5)
	private String mensagemUsuario;
	
	@ApiModelProperty(example = "2023-05-21-01T18:09:02.70844Z", position = 7)
	private OffsetDateTime dataHora;
	
	@ApiModelProperty(value = "Lista de ojetos ou campos que geraram o erro (opcional)", position = 8)
	private List<Campo> campos;
	
	@ApiModel("CampoProblema")
	@Getter
	@Builder
	public static class Campo {

		@ApiModelProperty(example = "valor")
		private String nome;
		
		@ApiModelProperty(example = "Valor campo obrigatório")
		private String mensagemUsuario;
		
		
	}
}
