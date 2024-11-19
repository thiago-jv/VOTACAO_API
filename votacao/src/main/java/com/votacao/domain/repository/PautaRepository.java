package com.votacao.domain.repository;

import com.votacao.domain.model.Pauta;
import com.votacao.domain.repository.pauta.PautaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long>, PautaRepositoryQuery {


    @Query("select p from Pauta p where p.id = :pIdPauta and p.habilitado = 'SIM'")
    Pauta listaPautaPorIdAndStatusHabilitaVotoSim(@Param("pIdPauta") Long pIdPauta);

    @Query("SELECT p FROM Pauta p " +
            "JOIN PautaAssociado pa ON p.id = pa.pauta.id " +
            "WHERE pa.associado.id = :associadoId AND p.habilitado = com.votacao.domain.model.enuns.SimNao.SIM")
    List<Pauta> findPautasPorAssociadoESituacaoSim(@Param("associadoId") Long associadoId);
}
