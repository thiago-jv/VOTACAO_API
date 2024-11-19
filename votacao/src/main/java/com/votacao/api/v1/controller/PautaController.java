package com.votacao.api.v1.controller;

import com.votacao.api.v1.dto.pauta.*;
import com.votacao.api.v1.icontroller.PautaControllerOpenApi;
import com.votacao.api.v1.mapper.PautaMapper;
import com.votacao.domain.model.Pauta;
import com.votacao.domain.repository.PautaRepository;
import com.votacao.domain.service.PautaAssociadoService;
import com.votacao.domain.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
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

    @Autowired
    private PautaAssociadoService pautaAssociadoService;

    @PostMapping
    @Override
    public PautaResponseDTO criarPauta(@RequestBody PautaPostDTO pautaPostDTO) {
        log.info("Iniciando Criação de Pauta");
        return pautaMapper.toPautaResponseDTO(pautaService.salvar(pautaPostDTO));
    }

    @PutMapping("/{idPauta}/habilitaVotacaoSim")
    @Override
    public void habilitarVotacao(@PathVariable Long idPauta) {
        log.info("Atualizando Status de votação para SIM " +idPauta);
        pautaService.habilitarVotacaoSim(idPauta);
    }

    @PutMapping("/{idPauta}/habilitaVotacaoNao")
    @Override
    public void desabilitarVotacao(@PathVariable Long idPauta) {
        log.info("Atualizando Status de votação para NÃO " +idPauta);
        pautaService.desabilitarVotacaoSim(idPauta);
    }

    @PutMapping("/{idPauta}/habilitaVotacaoFechado")
    @Override
    public void fecharVotacao(@PathVariable Long idPauta) {
        log.info("Atualizando Status de votação para FECHADO e criando fila no rabitMQ "  +idPauta);
        pautaService.fecharVotacao(idPauta);
    }

    @GetMapping
    @Override
    public Page<Pauta> pesquisarVotacao(PautaFilterDTO pautaFilterDTO, Pageable pageable) {
        return pautaRepository.filtrar(pautaFilterDTO, pageable);
    }

    @GetMapping("/{id}")
    @Override
    public PautaResponseDTO obterPautaPorId(@PathVariable Long id) {
        log.info("Busca Votação por ID " +id);
        return pautaMapper.toPautaResponseDTO(pautaService.buscarOuFalhar(id));
    }

    @GetMapping("/{idPauta}/pautasAssociados")
    @Override
    public PautaAssociadoResponseDTO listarPautasAssociados(@PathVariable Long idPauta) {
        log.info("Busca Pautas Associados");
        return pautaAssociadoService.obterPautasAssociadas(idPauta);
    }

    @PutMapping
    @Override
    public PautaResponseDTO atualizarPauta(@RequestBody PautaPutDTO pautaPutDTO) {
        log.info("Atualizando Pauta com ID " + pautaPutDTO.getId());
        Pauta pautaAtualizada = pautaService.atualizarPautaAssociados(pautaPutDTO);
        return pautaMapper.toPautaResponseDTO(pautaAtualizada);
    }

    @GetMapping("/associado/{id}/sim")
    public List<PautaResponseDTO> listarPautasPorAssociado(@PathVariable("id") Long idAssociado) {
        log.info("Busca pauta por associado " + idAssociado);
        return pautaMapper.toPautaResponseDTO(pautaService.listarPautasPorAssociado(idAssociado));
    }

}
