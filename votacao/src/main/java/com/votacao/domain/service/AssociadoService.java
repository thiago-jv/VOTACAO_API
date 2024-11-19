package com.votacao.domain.service;

import com.votacao.domain.model.Associado;
import com.votacao.domain.repository.AssociadoRepository;
import com.votacao.infra.exception.AssociadoNaoEncontadoException;
import com.votacao.infra.exception.EntidadeEmUsoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static java.lang.String.*;

@Service
public class AssociadoService implements Serializable {

    @Autowired
    private AssociadoRepository associadoRepository;

    public Associado buscarOuFalhar(Long id) {
        return associadoRepository.findById(id).orElseThrow(() -> new AssociadoNaoEncontadoException(id));
    }

    public Associado atualizar(Long idValor, Associado associado) {
        var associadoSalva = this.associadoRepository.findById(idValor)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(associado, associadoSalva, "id");
        return associadoRepository.save(associadoSalva);
    }

    public void excluir(Long idAssociado) {
        try {
            associadoRepository.deleteById(idAssociado);
            associadoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new AssociadoNaoEncontadoException(idAssociado);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(format("Entidade não pode ser deletada, pois está em uso", idAssociado));
        }
    }
}
