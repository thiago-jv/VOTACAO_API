package com.votacao.api.v1.mapper;

import com.votacao.api.v1.dto.associado.AssociadoPostDTO;
import com.votacao.api.v1.dto.associado.AssociadoResponseDTO;
import com.votacao.domain.model.Associado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

    Associado toAssociado(AssociadoPostDTO associadoPostDTO);

    AssociadoResponseDTO toAssociadoResponseDTO(Associado associado);


}
