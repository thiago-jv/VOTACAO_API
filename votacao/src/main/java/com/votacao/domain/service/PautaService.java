package com.votacao.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.votacao.domain.dto.VotacaoDTO;
import com.votacao.domain.model.Pauta;
import com.votacao.domain.model.Votacao;
import com.votacao.domain.model.enuns.SimNao;
import com.votacao.domain.repository.PautaRepository;
import com.votacao.infra.exception.NegocioException;
import com.votacao.infra.exception.PautaNaoEncontadoException;
import com.votacao.infra.exception.PautaNaoHabilitadaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Service
public class PautaService implements Serializable {

    @Autowired
    private PautaRepository pautaRepository;


    @Autowired
    private VotacaoProducerService votacaoProducerService;

    private static Logger log = Logger.getLogger(PautaService.class.getName());

    public Pauta buscarOuFalhar(Long id) {
        log.info("Realizando busca de Pauta por id: " + id);
        return pautaRepository.findById(id).orElseThrow(() -> new PautaNaoEncontadoException(id));
    }

    public void atualizarStatusHabilitadoSim(Long id) {
        log.info("Realizando atualização da pauta para SIM por id: " + id);
        var pauta = buscarOuFalhar(id);
        pauta.setHabilitado(SimNao.SIM);
        pautaRepository.save(pauta);
    }

    public void atualizarStatusHabilitadoNao(Long id) {
        log.info("Realizando atualização da pauta para NAO por id: " + id);
        var pauta = buscarOuFalhar(id);
        pauta.setHabilitado(SimNao.NAO);
        pautaRepository.save(pauta);
    }

    public void atualizarStatusHabilitadoFechado(Long id) {
        log.info("Realizando verificando se Pauta está com status de Habilitada SIM para o id: " + id);
        var pauta = buscarOuFalhar(id);
        if (pauta.getHabilitado() == SimNao.NAO) {
            throw new PautaNaoHabilitadaException(id);
        }
        pauta.setHabilitado(SimNao.FECHADO);
        var pautaSalva = pautaRepository.save(pauta);

        try {
            toVotacaoProducer(pautaSalva);
        } catch (JsonProcessingException e) {
            log.info("Lançando exception de lançamento de fila no rabitMQ " +e.getMessage());
             throw new NegocioException(e.getMessage());
        }

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

    public void toVotacaoProducer(Pauta pauta) throws JsonProcessingException {
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
        votacaoProducerService.sendMessage(objectMapper.writeValueAsString(votacaoDTO));
    }

}
