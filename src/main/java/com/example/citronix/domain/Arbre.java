package com.example.citronix.domain;

import com.example.citronix.domain.enums.Sante;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
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

    @OneToMany(mappedBy = "arbre")
    List<RecoltesDetails> recoltesDetailsList;


    public Integer getAge() {
        return Period.between(this.date_de_plantation.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
    }

    public double calculteAnnualProductivity() {
        int age = getAge();
        if (age < 3) return 2.5;
        if (age <= 10) return 12.0;
        if (age <= 20) return 20.0;
        return 0;
    }



}
