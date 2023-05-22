package com.votacao.api.v1.mapper;

import com.votacao.api.v1.dto.votacao.VotacaoPostDTO;
import com.votacao.api.v1.dto.votacao.VotacaoResponseDTO;
import com.votacao.domain.model.Votacao;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VotacaoMapper {

    Votacao toVotacao(VotacaoPostDTO votacaoPostDTO);

    List<VotacaoResponseDTO> toVotacaoResponseDTO(List<Votacao> votacao);


}
