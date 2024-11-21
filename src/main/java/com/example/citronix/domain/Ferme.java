package com.example.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ferme {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nom;
    private String localisation;
    private Double superficie;
    private LocalDateTime date_de_creation;

    @OneToMany(mappedBy = "ferme")
    List<Champ> champs;
}
