package com.votacao.domain.repository;

import com.votacao.domain.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    @Query("select p from Pauta p where p.id = :pIdPauta and p.habilitado = 'SIM'")
    Pauta listaPautaPorIdAndStatusHabilitaVotoSim(@Param("pIdPauta") Long pIdPauta);

}
