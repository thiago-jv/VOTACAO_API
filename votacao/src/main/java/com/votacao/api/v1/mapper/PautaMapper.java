package com.votacao.api.v1.mapper;

import com.votacao.api.v1.dto.pauta.PautaPostDTO;
import com.votacao.api.v1.dto.pauta.PautaResponseDTO;
import com.votacao.domain.model.Pauta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PautaMapper {

    Pauta toPauta(PautaPostDTO pautaPostDTO);

    PautaResponseDTO toPautaResponseDTO(Pauta pauta);

    List<PautaResponseDTO> toPautaResponseDTO(List<Pauta> pauta);


}
