package com.example.citronix.domain;

import com.example.citronix.domain.enums.ArbreAge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Arbre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime date_de_plantation;

    private ArbreAge age;

    @ManyToOne
    private Champ champ;

    private Double productivite_annuelle;

}
