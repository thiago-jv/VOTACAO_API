package com.votacao.domain.service;

import com.votacao.domain.model.Votacao;
import com.votacao.domain.repository.PautaRepository;
import com.votacao.domain.repository.VotacaoRepository;
import com.votacao.infra.exception.PautaNaoHabilitadaException;
import com.votacao.infra.exception.VotacaoAssociadoEncontadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class VotacaoService implements Serializable {

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoService associadoService;

    private static Logger log = Logger.getLogger(VotacaoService.class.getName());

    public List<Votacao> lancamentoVotacao(Votacao votacao) {
        Long idPauta = votacao.getPauta().getId();
        log.info("Realizando lançamento para a Pauta: " +idPauta);

        var pauta = pautaRepository.listaPautaPorIdAndStatusHabilitaVotoSim(idPauta);

        if (Objects.isNull(pauta)) {
            log.info("Validando se Pauta está habilitada SIM");
            throw new PautaNaoHabilitadaException(idPauta);
        }
        if (Objects.nonNull(idPauta)) {
            log.info("Validando se Associado está tentando votar mais de uma vez");
            validaVotoDuplicado(votacao, idPauta);
        }
        var associado = associadoService.buscarOuFalhar(votacao.getAssociado().getId());
        votacao.setPauta(pauta);
        votacao.setAssociado(associado);
        var votacaoSalva = votacaoRepository.save(votacao);
        pautaService.atualizaContagemVotoPauta(votacaoSalva, pauta);
        return getLocacaoPauta(votacaoSalva.getPauta().getId());
    }

    private void validaVotoDuplicado(Votacao votacao, Long idPauta) {
        List<Votacao> votacaosLancadas = getLocacaoPauta(idPauta);
        if (!votacaosLancadas.isEmpty()) {
            for (Votacao votoAssociado : votacaosLancadas) {
                if (votoAssociado.getAssociado().getId().equals(votacao.getAssociado().getId())) {
                    throw new VotacaoAssociadoEncontadoException(votoAssociado.getAssociado().getId());
                }
            }
        }
    }

    private List<Votacao> getLocacaoPauta(Long idPauta) {
        return votacaoRepository.listaVotacaoPorId(idPauta);
    }

}
