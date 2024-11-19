package com.example.citronix.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Ferme {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nom;
    private String localisation;
    private Double superficie;
    private LocalDateTime date_de_creation = LocalDateTime.now();

    @OneToMany
    List<Champ> champs;
}
