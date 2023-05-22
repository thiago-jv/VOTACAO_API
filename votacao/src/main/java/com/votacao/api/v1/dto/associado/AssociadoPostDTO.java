package com.votacao.api.v1.dto.associado;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AssociadoPostDTO implements Serializable {

    @ApiModelProperty(value = "Nome do Associado", example = "Joao de Melo", required = true)
    private String nome;

}
