package com.example.citronix.service.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Data
public class ChampDTO {
    @NotNull(message = "Le nom du ferme est obligé")
    @NotBlank(message = "il faut remplir le champ du ferme")
    private String ferme;

    @NotNull(message = "Le nom du nom est obligé")
    @NotBlank(message = "il faut remplir le champ du nom")
    private String nom;

    @NotNull(message = "Tu doit ajouter une valeur pour superficie")
    @Min(value = 100, message = "La superficie doit être au moins 1000")
    private double superficie;
}
