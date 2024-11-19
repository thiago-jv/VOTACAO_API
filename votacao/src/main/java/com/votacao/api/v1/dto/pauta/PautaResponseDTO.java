package com.votacao.api.v1.dto.pauta;

import com.votacao.domain.model.enuns.SimNao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PautaResponseDTO implements Serializable {

    private Long id;
    private int totalSim;
    private int totalNao;
    private int totalVoto;
    private SimNao habilitado;
    private String descricao;
    private LocalDateTime dataEntrada;



}
