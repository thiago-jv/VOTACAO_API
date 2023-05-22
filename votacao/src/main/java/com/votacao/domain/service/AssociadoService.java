package com.votacao.domain.service;

import com.votacao.domain.model.Associado;
import com.votacao.domain.repository.AssociadoRepository;
import com.votacao.infra.exception.AssociadoNaoEncontadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class AssociadoService implements Serializable {

    @Autowired
    private AssociadoRepository associadoRepository;

    public Associado buscarOuFalhar(Long id) {
        return associadoRepository.findById(id).orElseThrow(() -> new AssociadoNaoEncontadoException(id));
    }
}
