package com.votacao.api.v1.dto.pauta;

import com.votacao.api.v1.dto.associado.AssociadoResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PautaAssociadoResponseDTO implements Serializable {

    private Long id;
    private String descricao;
    private String habilitado;
    private List<AssociadoResponseDTO> associados;

    public PautaAssociadoResponseDTO(Long id, String descricao, List<AssociadoResponseDTO> associados, String habilitado) {
        this.id = id;
        this.descricao = descricao;
        this.associados = associados;
        this.habilitado = habilitado;
    }
}
