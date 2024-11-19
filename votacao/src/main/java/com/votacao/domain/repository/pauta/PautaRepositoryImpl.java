package com.votacao.domain.repository.pauta;

import com.votacao.api.v1.dto.pauta.PautaFilterDTO;
import com.votacao.domain.model.Pauta;
import com.votacao.domain.model.enuns.SimNao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PautaRepositoryImpl implements PautaRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Pauta> filtrar(PautaFilterDTO pautaFilterDTO, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pauta> criteria = builder.createQuery(Pauta.class);
        Root<Pauta> root = criteria.from(Pauta.class);

        criteria.orderBy(builder.asc(root.get("id")));

        Predicate[] predicates = criarRestricoes(pautaFilterDTO, builder, root);
        criteria.where(predicates);

        TypedQuery<Pauta> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(pautaFilterDTO));
    }

    private Predicate[] criarRestricoes(PautaFilterDTO pautaFilterDTO, CriteriaBuilder builder,
                                        Root<Pauta> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!Objects.isNull(pautaFilterDTO.getHabilitado())) {
            SimNao habilitado = SimNao.valueOf(pautaFilterDTO.getHabilitado().toUpperCase());

            predicates.add(builder.equal(root.get("habilitado"), habilitado));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(PautaFilterDTO pautaFilterDTO) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Pauta> root = criteria.from(Pauta.class);

        Predicate[] predicates = criarRestricoes(pautaFilterDTO, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
