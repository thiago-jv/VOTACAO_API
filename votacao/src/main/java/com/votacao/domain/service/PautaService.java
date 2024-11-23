package com.votacao.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.votacao.api.v1.dto.associado.AssociadoIdDTO;
import com.votacao.api.v1.dto.pauta.PautaPostDTO;
import com.votacao.api.v1.dto.pauta.PautaPutDTO;
import com.votacao.domain.dto.VotacaoDTO;
import com.votacao.domain.model.Associado;
import com.votacao.domain.model.Pauta;
import com.votacao.domain.model.PautaAssociado;
import com.votacao.domain.model.Votacao;
import com.votacao.domain.model.enuns.SimNao;
import com.votacao.domain.repository.PautaAssociadoRepository;
import com.votacao.domain.repository.PautaRepository;
import com.votacao.infra.exception.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PautaService implements Serializable {

    private PautaRepository pautaRepository;

    private VotacaoProducerService votacaoProducerService;

    private AssociadoService associadoService;

    private PautaAssociadoRepository pautaAssociadoRepository;

    public PautaService(PautaRepository pautaRepository, VotacaoProducerService votacaoProducerService, AssociadoService associadoService, PautaAssociadoRepository pautaAssociadoRepository) {
        this.pautaRepository = pautaRepository;
        this.votacaoProducerService = votacaoProducerService;
        this.associadoService = associadoService;
        this.pautaAssociadoRepository = pautaAssociadoRepository;
    }

    private static Logger log = Logger.getLogger(PautaService.class.getName());

    public Pauta salvar(PautaPostDTO pautaPostDTO) {
        log.info("Salvando pauta com os associados vinculados");
        Pauta pauta = pautaRepository.save(new Pauta(pautaPostDTO.getDescricao()));
        for (AssociadoIdDTO associado : pautaPostDTO.getAssociadosId()) {
            Associado associateDB = associadoService.buscarOuFalhar(associado.getId());
            pautaAssociadoRepository.save(new PautaAssociado(associateDB, pauta));
        }
        return pauta;
    }

    public Pauta buscarOuFalhar(Long id) {
        log.info("Realizando busca de Pauta por id: " + id);
        return pautaRepository.findById(id).orElseThrow(() -> new PautaNaoEncontadoException(id));
    }

    public void habilitarVotacaoSim(Long id) {
        log.info("Realizando atualização da pauta para SIM por id: " + id);
        var pauta = buscarOuFalhar(id);
        if(pauta.getHabilitado().equals(SimNao.SIM)){
            throw new PautaSimException(id);
        }

        pauta.setHabilitado(SimNao.SIM);
        pautaRepository.save(pauta);
    }

    public void desabilitarVotacaoSim(Long id) {
        log.info("Realizando atualização da pauta para NAO por id: " + id);
        var pauta = buscarOuFalhar(id);
        if(pauta.getHabilitado().equals(SimNao.NAO)){
            throw new PautaNaoException(id);
        }
        pauta.setHabilitado(SimNao.NAO);
        pautaRepository.save(pauta);
    }

    public void fecharVotacao(Long id) {
        log.info("Realizando verificando se Pauta está com status de Habilitada SIM para o id: " + id);
        var pauta = buscarOuFalhar(id);
        if(pauta.getHabilitado().equals(SimNao.FECHADO)){
            throw new PautaFechadaException(id);
        }
        pauta.setHabilitado(SimNao.FECHADO);
        var pautaSalva = pautaRepository.save(pauta);
         toVotacaoProducer(pautaSalva);
    }

    public void atualizaContagemVotoPauta(Votacao votacao, Pauta pauta) {
        log.info("Realizando contegem dos votos");
        if (votacao.getVoto() == SimNao.SIM) {
            pauta.setTotalSim(pauta.getTotalSim() + 1);
        }
        if (votacao.getVoto() == SimNao.NAO) {
            pauta.setTotalNao(pauta.getTotalNao() + 1);
        }
        pauta.setTotalVoto(pauta.getTotalSim() + pauta.getTotalNao());
        pautaRepository.save(pauta);
    }

    public void toVotacaoProducer(Pauta pauta) {
        log.info("Realizando montagem da classe DTO para envior da fila ao RabiMQ por pauta: " + pauta.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var votacaoDTO = new VotacaoDTO();
        votacaoDTO.setIdPauta(pauta.getId());
        votacaoDTO.setTotalSim(String.valueOf(pauta.getTotalSim()));
        votacaoDTO.setTotalNao(String.valueOf(pauta.getTotalNao()));
        votacaoDTO.setTotal(String.valueOf(pauta.getTotalVoto()));
        votacaoDTO.setPauta(pauta.getDescricao());
        votacaoDTO.setDataVotacao(formatter.format(pauta.getDataEntrada()));
        try {
            votacaoProducerService.sendMessage(objectMapper.writeValueAsString(votacaoDTO));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Pauta atualizarPautaAssociados(PautaPutDTO pautaPutDTO) {
        log.info("Iniciando a atualização da pauta associada com ID: " + pautaPutDTO.getId());

        Pauta pautaExistente = pautaRepository.findById(pautaPutDTO.getId())
                .orElseThrow(() -> new PautaNaoEncontadoException(pautaPutDTO.getId()));
        if (pautaPutDTO.getDescricao() != null) {
            pautaExistente.setDescricao(pautaPutDTO.getDescricao());
        }
        if (pautaPutDTO.getId() != null) {
            pautaAssociadoRepository.removerTodosAssociadosDaPauta(pautaPutDTO.getId());
            for (AssociadoIdDTO associadoId : pautaPutDTO.getAssociados()) {
                Associado associado = associadoService.buscarOuFalhar(associadoId.getId());
                PautaAssociado pautaAssociado = new PautaAssociado(associado, pautaExistente);
                pautaAssociadoRepository.save(pautaAssociado);
            }
        }
        return pautaRepository.save(pautaExistente);
    }

    public List<Pauta> listarPautasPorAssociado(Long associadoId) {
        return pautaRepository.findPautasPorAssociadoESituacaoSim(associadoId);
    }


}
