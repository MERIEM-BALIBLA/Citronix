package com.example.citronix.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Champ {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Ferme ferme;

    private String nom;

    private double superficie;

    @OneToMany(mappedBy = "champ", fetch = FetchType.EAGER)
    List<Arbre> arbres;

}
