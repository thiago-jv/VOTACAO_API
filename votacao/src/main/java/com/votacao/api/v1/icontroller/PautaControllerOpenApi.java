package com.votacao.api.v1.icontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.votacao.api.v1.dto.pauta.PautaPostDTO;
import com.votacao.api.v1.dto.pauta.PautaResponseDTO;
import com.votacao.infra.exception.handler.Problema;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Pauta")
public interface PautaControllerOpenApi {

    @ApiOperation("Cria uma nova pauta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pauta cadastrado"),
    })
    PautaResponseDTO criar(
            @ApiParam(name = "corpo", value = "Representação de uma pauta") PautaPostDTO pautaPostDTO);

    @ApiOperation("Atualiza Pauta de Votação por status para SIM")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pauta de Votação não encontrado", response = Problema.class),
    })
    void atualizarStatusHabilitadoSim(@PathVariable Long id);

    @ApiOperation("Atualiza Pauta de Votação por status para NAO")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pauta de Votação não encontrado", response = Problema.class),
    })
    void atualizarStatusHabilitadoNao(@PathVariable Long id);

    @ApiOperation("Atualiza Pauta de Votação por status para FECHADO, com isso enviado uma fila para o rabitmq nomeada 'VOTACAO_RESULTADO'")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pauta de Votação não encontrado", response = Problema.class),
    })
    void atualizarStatusFechado(@PathVariable Long id) throws JsonProcessingException;


}
