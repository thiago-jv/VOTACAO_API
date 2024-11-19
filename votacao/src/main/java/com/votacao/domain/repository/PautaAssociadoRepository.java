package com.votacao.domain.repository;

import com.votacao.domain.model.PautaAssociado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaAssociadoRepository extends JpaRepository<PautaAssociado, Long> {

    @Query("select p from PautaAssociado p where p.pauta.id = :pIdPauta")
    List<PautaAssociado> listaPautaAssociadoPorId(@Param("pIdPauta") Long pIdPauta);

    @Modifying
    @Query("DELETE FROM PautaAssociado pa WHERE pa.pauta.id = :idPauta")
    void removerTodosAssociadosDaPauta(@Param("idPauta") Long idPauta);




}
