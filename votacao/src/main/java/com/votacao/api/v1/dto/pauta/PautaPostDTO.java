package com.votacao.api.v1.dto.pauta;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class PautaPostDTO implements Serializable {

    @ApiModelProperty(value = "Descrição da Pauta", example = "Votação para admissão de novo dev Java", required = true)
    private String descricao;

}
