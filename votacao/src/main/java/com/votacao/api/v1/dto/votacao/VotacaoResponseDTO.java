package com.votacao.api.v1.dto.votacao;

import com.votacao.api.v1.dto.associado.AssociadoIdDTO;
import com.votacao.api.v1.dto.enuns.SimNao;
import com.votacao.api.v1.dto.pauta.PautaIdDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VotacaoResponseDTO implements Serializable {

    private Long id;
    private SimNao voto;
    private PautaIdDTO pauta;
    private AssociadoIdDTO associado;

}
