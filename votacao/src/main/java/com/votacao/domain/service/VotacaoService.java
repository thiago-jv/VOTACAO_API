package com.votacao.domain.service;

import com.votacao.domain.model.Votacao;
import com.votacao.domain.model.enuns.SimNao;
import com.votacao.domain.repository.PautaRepository;
import com.votacao.domain.repository.VotacaoRepository;
import com.votacao.infra.exception.EntidadeNaoEncontradaException;
import com.votacao.infra.exception.PautaNaoHabilitadaException;
import com.votacao.infra.exception.VotacaoAssociadoEncontadoException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class VotacaoService implements Serializable {

    private VotacaoRepository votacaoRepository;

    private PautaRepository pautaRepository;

    private PautaService pautaService;

    private AssociadoService associadoService;

    public VotacaoService(VotacaoRepository votacaoRepository, PautaRepository pautaRepository, PautaService pautaService, AssociadoService associadoService) {
        this.votacaoRepository = votacaoRepository;
        this.pautaRepository = pautaRepository;
        this.pautaService = pautaService;
        this.associadoService = associadoService;
    }

    private static Logger log = Logger.getLogger(VotacaoService.class.getName());

    public List<Votacao> lancamentoVotacao(Votacao votacao) {
        log.info("Iniciando o lançamento de voto para a pauta.");

        validarAssociado(votacao);

        validarPauta(votacao);

        validarVoto(votacao);

        Long idPauta = votacao.getPauta().getId();

        var pauta = pautaRepository.listaPautaPorIdAndStatusHabilitaVotoSim(idPauta);

        if (!pauta.getHabilitado().equals(SimNao.SIM)) {
            log.warning("Pauta com ID {} não está habilitada para votação ou não encontrada.");
            throw new PautaNaoHabilitadaException(idPauta);
        }

        log.info("Verificando se o associado já votou na pauta com ID {}.");
        validaVotoDuplicado(votacao, idPauta);

        var associado = associadoService.buscarOuFalhar(votacao.getAssociado().getId());
        votacao.setPauta(pauta);
        votacao.setAssociado(associado);

        var votacaoSalva = votacaoRepository.save(votacao);

        pautaService.atualizaContagemVotoPauta(votacaoSalva, pauta);

        return getLocacaoPauta(votacaoSalva.getPauta().getId());
    }

    private void validarAssociado(Votacao votacao) {
        if (Objects.isNull(votacao.getAssociado()) || Objects.isNull(votacao.getAssociado().getId())) {
            log.warning("Associado não informado ou inválido.");
            throw new EntidadeNaoEncontradaException("Favor informar um associado.");
        }
    }

    private void validarPauta(Votacao votacao) {
        if (Objects.isNull(votacao.getPauta()) || Objects.isNull(votacao.getPauta().getId())) {
            log.warning("Pauta não informada ou inválida.");
            throw new EntidadeNaoEncontradaException("Favor informar uma pauta.");
        }
    }

    private void validarVoto(Votacao votacao) {
        if (Objects.isNull(votacao.getVoto())) {
            log.warning("Voto não informado ou inválida.");
            throw new EntidadeNaoEncontradaException("Favor informar um voto.");
        }
    }

    private void validaVotoDuplicado(Votacao votacao, Long idPauta) {
        List<Votacao> votacoesLancadas = getLocacaoPauta(idPauta);
        for (Votacao votoAssociado : votacoesLancadas) {
            if (votoAssociado.getAssociado().getId().equals(votacao.getAssociado().getId())) {
                log.info("Associado com ID {} já votou na pauta com ID {}.");
                throw new VotacaoAssociadoEncontadoException(votoAssociado.getAssociado().getId());
            }
        }
    }

    private List<Votacao> getLocacaoPauta(Long idPauta) {
        return votacaoRepository.listaVotacaoPorId(idPauta);
    }
}
