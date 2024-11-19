package com.votacao.api.v1.mapper;

import com.votacao.api.v1.dto.associado.AssociadoPostDTO;
import com.votacao.api.v1.dto.associado.AssociadoPutDTO;
import com.votacao.api.v1.dto.associado.AssociadoResponseDTO;
import com.votacao.domain.model.Associado;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

    Associado toAssociado(AssociadoPostDTO associadoPostDTO);

    Associado toAssociado(AssociadoPutDTO associadoPutDTO);

    AssociadoResponseDTO toAssociadoResponseDTO(Associado associado);

    List<AssociadoResponseDTO> toListAssociadoResponseDTO(List<Associado> associado);


}
