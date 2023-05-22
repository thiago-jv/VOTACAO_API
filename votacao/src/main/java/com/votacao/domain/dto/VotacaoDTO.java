package com.votacao.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotacaoDTO {

    private Long idPauta;
    private String totalSim;
    private String totalNao;
    private String total;
    private String pauta;
    private String dataVotacao;

}
