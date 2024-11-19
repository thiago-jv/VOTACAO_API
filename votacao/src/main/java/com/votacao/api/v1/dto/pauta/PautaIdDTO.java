package com.votacao.api.v1.dto.pauta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PautaIdDTO implements Serializable {

    @ApiModelProperty(value = "Código da Pauta", example = "1", required = true)
    private Long id;

    public PautaIdDTO() {
    }

    public PautaIdDTO(Long id) {
        this.id = id;
    }
}
