package com.votacao.api.v1.controller;

import com.votacao.api.v1.dto.votacao.VotacaoPostDTO;
import com.votacao.api.v1.dto.votacao.VotacaoResponseDTO;
import com.votacao.api.v1.icontroller.VotacaoControllerOpenApi;
import com.votacao.api.v1.mapper.VotacaoMapper;
import com.votacao.domain.service.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/votacoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class VotacaoController implements Serializable, VotacaoControllerOpenApi {

    @Autowired
    private VotacaoService votacaoService;

    @Autowired
    private VotacaoMapper votacaoMapper;

    private static Logger log = Logger.getLogger(VotacaoController.class.getName());

    @PostMapping
    @Override
    public List<VotacaoResponseDTO> criarVotacao(@RequestBody VotacaoPostDTO votacaoPostDTO) {
        log.info("Iniciando Criação de Votação");
        return votacaoMapper.toVotacaoResponseDTO(votacaoService.lancamentoVotacao(votacaoMapper.toVotacao(votacaoPostDTO)));
    }

}
