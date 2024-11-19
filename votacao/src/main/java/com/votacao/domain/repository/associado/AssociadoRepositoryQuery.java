package com.votacao.domain.repository.associado;

import com.votacao.api.v1.dto.associado.AssociadoFilterDTO;
import com.votacao.domain.model.Associado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssociadoRepositoryQuery {

    Page<Associado> filtrar(AssociadoFilterDTO associadoFilterDTO, Pageable pageable);
}
