package com.votacao.domain.repository.pauta;

import com.votacao.api.v1.dto.pauta.PautaFilterDTO;
import com.votacao.domain.model.Pauta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PautaRepositoryQuery {

    Page<Pauta> filtrar(PautaFilterDTO pautaFilterDTO, Pageable pageable);
}
