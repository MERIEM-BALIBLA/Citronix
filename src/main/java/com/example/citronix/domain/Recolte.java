package com.example.citronix.domain;

import com.example.citronix.domain.enums.Saison;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Recolte {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Saison saison;
    private LocalDateTime date_de_recolte;
    private Double quatiteTotale;

    @OneToMany(mappedBy = "recolte")
    private List<RecoltesDetails> recoltesDetails;

}
