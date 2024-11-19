package com.votacao.api.v1.icontroller;

import com.votacao.api.v1.dto.votacao.VotacaoPostDTO;
import com.votacao.api.v1.dto.votacao.VotacaoResponseDTO;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Votacao")
public interface VotacaoControllerOpenApi {

    @ApiOperation("Cria uma nova sessão de votação")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sessão de votação, onde seram lançados os votos e todo o processo enquanto durar a sessão controlada pela PAUTA"),
    })
    List<VotacaoResponseDTO> criarVotacao(
            @ApiParam(name = "corpo", value = "Representação de uma votação") VotacaoPostDTO votacaoPostDTO);
}
