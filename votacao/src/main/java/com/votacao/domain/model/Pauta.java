package com.votacao.domain.model;

import com.sun.istack.NotNull;
import com.votacao.domain.model.enuns.SimNao;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "PAUTA", schema = "public")
public class Pauta implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "TOTAL_SIM")
    private int totalSim;

    @Column(name = "TOTAL_NAO")
    private int totalNao;

    @Column(name = "TOTAL_VOTO")
    private int totalVoto;

    @NotNull
    @Column(name = "HABILITADO")
    @Enumerated(EnumType.STRING)
    private SimNao habilitado = SimNao.NAO;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "DATA_PAUTA", nullable = false)
    private LocalDateTime dataEntrada = LocalDateTime.now();

}
