package com.votacao.domain.repository;

import com.votacao.domain.model.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {

    @Query("select v from Votacao v inner join fetch v.associado a inner join fetch v.pauta p where p.id = :pIdPauta")
    List<Votacao> listaVotacaoPorId(@Param("pIdPauta") Long pIdPauta);

}
