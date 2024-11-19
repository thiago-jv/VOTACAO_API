package com.votacao.domain.repository;

import com.votacao.domain.model.Associado;
import com.votacao.domain.repository.associado.AssociadoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long>, AssociadoRepositoryQuery {
}
