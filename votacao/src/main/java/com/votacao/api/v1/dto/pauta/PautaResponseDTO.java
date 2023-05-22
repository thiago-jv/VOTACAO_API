package com.votacao.api.v1.dto.pauta;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PautaResponseDTO implements Serializable {

    private Long id;
    private String descricao;

}
