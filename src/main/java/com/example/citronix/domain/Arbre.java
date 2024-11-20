package com.example.citronix.domain;

import com.example.citronix.domain.enums.ArbreAge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Arbre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime date_de_plantation;

    @ManyToOne
    private Champ champ;

    public ArbreAge getAge() {
        long nombreDAnneesDepuisPlantation = ChronoUnit.YEARS.between(this.date_de_plantation, LocalDateTime.now());

        if (nombreDAnneesDepuisPlantation < 5) {
            return ArbreAge.YOUNG;
        } else if (nombreDAnneesDepuisPlantation < 15) {
            return ArbreAge.MATURE;
        } else {
            return ArbreAge.OLD;
        }
    }

//    public double getProductivite() {
//        ArbreAge age = getAge();
//        return age.getV();
//    }


}
