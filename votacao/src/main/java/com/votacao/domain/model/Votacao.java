package com.votacao.domain.model;

import com.sun.istack.NotNull;
import com.votacao.domain.model.enuns.SimNao;
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
@Table(name = "VOTACAO", schema = "public")
public class Votacao implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "VOTO", nullable = false)
    @Enumerated(EnumType.STRING)
    private SimNao voto;

    @ManyToOne
    @JoinColumn(name = "ID_PAUTA", referencedColumnName = "ID", nullable = false)
    private Pauta pauta;

    @ManyToOne
    @JoinColumn(name = "ID_ASSOCIADO", referencedColumnName = "ID", nullable = false)
    private Associado associado;

}
