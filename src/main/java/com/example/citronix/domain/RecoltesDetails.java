package com.example.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
public class RecoltesDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Arbre arbre;

    @ManyToOne
    private Recolte recolte;

    private Double quantite;
}
