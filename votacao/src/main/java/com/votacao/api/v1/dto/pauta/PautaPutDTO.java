package com.votacao.api.v1.dto.pauta;

import com.votacao.api.v1.dto.associado.AssociadoIdDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;@Getter
@Setter
public class PautaPutDTO implements Serializable {

    private Long id;
    private String descricao;
    private List<AssociadoIdDTO> associados;

}


