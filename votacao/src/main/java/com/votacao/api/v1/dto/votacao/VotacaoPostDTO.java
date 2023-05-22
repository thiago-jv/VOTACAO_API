package com.votacao.api.v1.dto.votacao;

import com.votacao.api.v1.dto.associado.AssociadoIdDTO;
import com.votacao.api.v1.dto.enuns.SimNao;
import com.votacao.api.v1.dto.pauta.PautaIdDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VotacaoPostDTO implements Serializable {

    @ApiModelProperty(value = "Status", example = "SIM", required = true)
    private SimNao voto;

    @ApiModelProperty(value = "Código da pauta", example = "1", required = true)
    private PautaIdDTO pauta;

    @ApiModelProperty(value = "Código do associado", example = "1", required = true)
    private AssociadoIdDTO associado;

}
