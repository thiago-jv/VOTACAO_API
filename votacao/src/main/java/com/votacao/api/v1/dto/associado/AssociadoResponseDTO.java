package com.votacao.api.v1.dto.associado;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AssociadoResponseDTO implements Serializable {

    private Long id;
    private String nome;

}
