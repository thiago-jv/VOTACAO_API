package com.votacao.api.v1.dto.pauta;

import com.votacao.api.v1.dto.associado.AssociadoIdDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PautaPostDTO implements Serializable {

    @ApiModelProperty(value = "Descrição da Pauta", example = "Votação para admissão de novo dev Java", required = true)
    private String descricao;

    @ApiModelProperty(value = "Ids dos associados para essa pauta", example = "[1,3,6,7,9]", required = true)
    private List<AssociadoIdDTO> associadosId;

}
