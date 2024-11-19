package com.votacao.domain.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "ASSOCIADO", schema = "public")
public class Associado implements Serializable {


    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "associado_seq")
    @SequenceGenerator(name = "associado_seq", sequenceName = "associado_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(min = 1, max = 90)
    @Column(name = "NOME", nullable = false)
    private String nome;

}
