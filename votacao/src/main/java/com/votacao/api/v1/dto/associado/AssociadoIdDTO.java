package com.votacao.api.v1.dto.associado;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AssociadoIdDTO implements Serializable {

    @ApiModelProperty(value = "Código do Associado", example = "1", required = true)
    private Long id;
}
