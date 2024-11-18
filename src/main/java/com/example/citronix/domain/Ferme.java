package com.example.citronix.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Ferme {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nom;
    private String localisation;
    private Double superficie;
    private LocalDateTime date_de_creation;

//    @OneToMany
//    List<Champ> champs;
}
