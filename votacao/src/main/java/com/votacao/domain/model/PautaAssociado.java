package com.votacao.domain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "PAUTA_ASSOCIADO", schema = "public")
public class PautaAssociado implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pautaassociado_seq")
    @SequenceGenerator(name = "pautaassociado_seq", sequenceName = "pautaassociado_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ASSOCIADO", referencedColumnName = "ID")
    private Associado associado;

    @ManyToOne
    @JoinColumn(name = "ID_PAUTA", referencedColumnName = "ID", nullable = false)
    private Pauta pauta;

    public PautaAssociado(Associado associado, Pauta pauta) {
        this.associado = associado;
        this.pauta = pauta;
    }
}
