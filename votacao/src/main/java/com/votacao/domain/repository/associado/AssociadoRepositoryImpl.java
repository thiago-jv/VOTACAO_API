package com.votacao.domain.repository.associado;

import com.votacao.api.v1.dto.associado.AssociadoFilterDTO;
import com.votacao.domain.model.Associado;
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

public class AssociadoRepositoryImpl implements AssociadoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Associado> filtrar(AssociadoFilterDTO associadoFilterDTO, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Associado> criteria = builder.createQuery(Associado.class);
        Root<Associado> root = criteria.from(Associado.class);

        criteria.orderBy(builder.asc(root.get("id")));

        Predicate[] predicates = criarRestricoes(associadoFilterDTO, builder, root);
        criteria.where(predicates);

        TypedQuery<Associado> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(associadoFilterDTO));
    }

    private Predicate[] criarRestricoes(AssociadoFilterDTO associadoFilterDTO, CriteriaBuilder builder,
                                        Root<Associado> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!Objects.isNull(associadoFilterDTO.getNome())) {

            predicates.add(builder.like((root.get("nome")),
                    "%" + associadoFilterDTO.getNome() + "%"));
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

    private Long total(AssociadoFilterDTO associadoFilterDTO) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Associado> root = criteria.from(Associado.class);

        Predicate[] predicates = criarRestricoes(associadoFilterDTO, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

}
