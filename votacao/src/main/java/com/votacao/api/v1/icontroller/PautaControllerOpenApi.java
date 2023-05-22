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

    @ApiOperation("Atualiza Pauta de Votação por status para SIM, dessa forma habilitando para votação")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pauta de Votação não encontrado", response = Problema.class),
    })
    void atualizarStatusHabilitadoSim(@PathVariable Long id);

    @ApiOperation("Atualiza Pauta de Votação por status para NAO, dessa forma não habilitando para votação")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pauta de Votação não encontrado", response = Problema.class),
    })
    void atualizarStatusHabilitadoNao(@PathVariable Long id);

    @ApiOperation("Atualiza Pauta de votação para status de FECHADO, dessa fnalizando sessão e criando um 'producer-fila' no RabitMQ nomeada 'VOTACAO_RESULTADO', vale resaltar que para FECHADO, o status deve ser Habilitado para votação SIM")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pauta de Votação não encontrado", response = Problema.class),
    })
    void atualizarStatusFechado(@PathVariable Long id) throws JsonProcessingException;


}
