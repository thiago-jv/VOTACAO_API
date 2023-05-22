package com.votacao.api.v1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.votacao.api.v1.dto.pauta.PautaPostDTO;
import com.votacao.api.v1.dto.pauta.PautaResponseDTO;
import com.votacao.api.v1.icontroller.PautaControllerOpenApi;
import com.votacao.api.v1.mapper.PautaMapper;
import com.votacao.domain.repository.PautaRepository;
import com.votacao.domain.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/pautas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PautaController implements Serializable, PautaControllerOpenApi {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private PautaMapper pautaMapper;

    private static Logger log = Logger.getLogger(PautaController.class.getName());

    @PostMapping
    @Override
    public PautaResponseDTO criar(@RequestBody PautaPostDTO pautaPostDTO) {
        log.info("Iniciando Criação de Pauta");
        return pautaMapper.toPautaResponseDTO(pautaRepository.save(pautaMapper.toPauta(pautaPostDTO)));
    }

    @PutMapping("/{idPauta}/habilitaVotacaoSim")
    @Override
    public void atualizarStatusHabilitadoSim(@PathVariable Long idPauta) {
        log.info("Atualizando Status de votação para SIM");
        pautaService.atualizarStatusHabilitadoSim(idPauta);
    }

    @PutMapping("/{idPauta}/habilitaVotacaoNao")
    @Override
    public void atualizarStatusHabilitadoNao(@PathVariable Long idPauta) {
        log.info("Atualizando Status de votação para NÃO");
        pautaService.atualizarStatusHabilitadoNao(idPauta);
    }

    @PutMapping("/{idPauta}/habilitaVotacaoFechado")
    @Override
    public void atualizarStatusFechado(@PathVariable Long idPauta) {
        log.info("Atualizando Status de votação para FECHADO e criando fila no rabitMQ");
        pautaService.atualizarStatusHabilitadoFechado(idPauta);
    }

}
