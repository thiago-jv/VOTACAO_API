package com.votacao.domain.service;

import com.votacao.api.v1.dto.associado.AssociadoResponseDTO;
import com.votacao.api.v1.dto.pauta.PautaAssociadoResponseDTO;
import com.votacao.domain.model.PautaAssociado;
import com.votacao.domain.repository.PautaAssociadoRepository;
import com.votacao.infra.exception.PautaNaoEncontadoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class PautaAssociadoService {

    private PautaAssociadoRepository pautaAssociadoRepository;

    private static Logger log = Logger.getLogger(PautaAssociadoService.class.getName());

    public PautaAssociadoService(PautaAssociadoRepository pautaAssociadoRepository) {
        this.pautaAssociadoRepository = pautaAssociadoRepository;
    }

    public PautaAssociadoResponseDTO obterPautasAssociadas(Long pautaId) {
        List<PautaAssociado> pautasAssociadas = buscarOuFalhar(pautaId);
        List<AssociadoResponseDTO> associadosDTO = new ArrayList<>();
        String descricao= "";
        String habilitado = "";

        for (PautaAssociado pautaAssociado : pautasAssociadas) {
            if(Objects.nonNull(pautaAssociado.getPauta()) &&
                    Objects.nonNull(pautaAssociado.getPauta().getDescricao())){
                descricao = pautaAssociado.getPauta().getDescricao();
            }
            habilitado = pautaAssociado.getPauta().getHabilitado().toString();

            if (Objects.nonNull(pautaAssociado.getAssociado()) &&
                    Objects.nonNull(pautaAssociado.getAssociado().getId()) &&
                    Objects.nonNull(pautaAssociado.getAssociado().getNome())) {

                associadosDTO.add(new AssociadoResponseDTO(
                        pautaAssociado.getAssociado().getId(),
                        pautaAssociado.getAssociado().getNome()
                ));
            }
        }
        return new PautaAssociadoResponseDTO(pautaId, descricao, associadosDTO, habilitado);
    }

    public List<PautaAssociado> buscarOuFalhar(Long id) {
        log.info("Realizando busca de Pauta por id: " + id);
        List<PautaAssociado> pautaAssociados = pautaAssociadoRepository.listaPautaAssociadoPorId(id);
        if(Objects.nonNull(pautaAssociados) && pautaAssociados.size() > 0){
            return pautaAssociados;
        } else {
            throw new PautaNaoEncontadoException(id);
        }
    }

}
