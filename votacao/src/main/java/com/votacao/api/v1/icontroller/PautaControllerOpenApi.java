package com.votacao.api.v1.icontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.votacao.api.v1.dto.pauta.*;
import com.votacao.domain.model.Pauta;
import com.votacao.infra.exception.handler.Problema;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Pauta")
public interface PautaControllerOpenApi {

    @ApiOperation("Cria uma nova pauta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pauta cadastrada com sucesso"),
            @ApiResponse(code = 400, message = "Dados inválidos fornecidos")
    })
    @PostMapping
    PautaResponseDTO criarPauta(
            @ApiParam(name = "corpo", value = "Representação de uma nova pauta", required = true)
            @RequestBody PautaPostDTO pautaPostDTO);

    @ApiOperation("Atualiza Pauta de Votação para status SIM, habilitando para votação")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pauta habilitada para votação"),
            @ApiResponse(code = 404, message = "Pauta de Votação não encontrada", response = Problema.class)
    })
    @PutMapping("/{id}/habilitarSim")
    void habilitarVotacao(
            @ApiParam(value = "ID da pauta", required = true)
            @PathVariable Long id);

    @ApiOperation("Atualiza Pauta de Votação para status NAO, desabilitando para votação")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pauta desabilitada para votação"),
            @ApiResponse(code = 404, message = "Pauta de Votação não encontrada", response = Problema.class)
    })
    @PutMapping("/{id}/habilitarNao")
    void desabilitarVotacao(
            @ApiParam(value = "ID da pauta", required = true)
            @PathVariable Long id);

    @ApiOperation("Atualiza Pauta de votação para status FECHADO, finalizando a sessão e gerando evento no RabbitMQ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pauta fechada com sucesso"),
            @ApiResponse(code = 404, message = "Pauta de Votação não encontrada", response = Problema.class),
            @ApiResponse(code = 500, message = "Erro ao processar a solicitação", response = Problema.class)
    })
    @PutMapping("/{id}/fechar")
    void fecharVotacao(
            @ApiParam(value = "ID da pauta", required = true)
            @PathVariable Long id) throws JsonProcessingException;

    @ApiOperation("Busca todas as pautas paginadas com filtros")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada com sucesso")
    })
    @GetMapping
    Page<Pauta> pesquisarVotacao(
            @ApiParam(name = "filtro", value = "Filtros para pesquisa de pautas")
            @Valid PautaFilterDTO pautaFilterDTO,
            Pageable pageable);

    @ApiOperation("Busca uma pauta pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pauta encontrada"),
            @ApiResponse(code = 404, message = "Pauta não encontrada")
    })
    @GetMapping("/{id}")
    PautaResponseDTO obterPautaPorId(
            @ApiParam(value = "ID da pauta", required = true)
            @PathVariable Long id);

    @ApiOperation("Lista os associados de uma pauta específica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Associados encontrados para a pauta"),
            @ApiResponse(code = 404, message = "Pauta não encontrada")
    })
    @GetMapping("/{idPauta}/associados")
    PautaAssociadoResponseDTO listarPautasAssociados(
            @ApiParam(value = "ID da pauta", required = true)
            @PathVariable Long idPauta);

    @ApiOperation("Atualiza os dados de uma pauta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pauta atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Dados inválidos fornecidos"),
            @ApiResponse(code = 404, message = "Pauta não encontrada")
    })
    @PutMapping
    PautaResponseDTO atualizarPauta(@RequestBody PautaPutDTO pautaPutDTO);

    @ApiOperation("Lista as pautas vinculadas a um associado")
    @GetMapping("/associado/{id}")
    public List<PautaResponseDTO> listarPautasPorAssociado(@PathVariable("id") Long idAssociado);
}
