package com.example.citronix.web.VM;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
public class RecoltesDetailsVM {
//    @NotNull(message = "il faut remplir le champ d'arbre")
    private UUID arbre;

    @NotNull(message = "il faut remplir le champ de recolte")
    private UUID recolte;

    @NotNull(message = "il faut remplir le champ de la quantité")
    private double quantite;
}
