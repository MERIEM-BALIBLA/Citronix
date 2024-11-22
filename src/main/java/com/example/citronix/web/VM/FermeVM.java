package com.example.citronix.web.VM;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class FermeVM {
    @NotNull(message = "le nom de la ferme ne doit pas etre null")
    @NotBlank(message = "s'il vous plait il faut remplir le champ du nom")
    private String nom;

    @NotNull(message = "le localistation de la ferme ne doit pas etre null")
    @NotBlank(message = "s'il vous plait il faut remplir le champ du localistation")
    private String localisation;

    @NotNull(message = "le superficie de la ferme ne doit pas etre null")
    @Min(value = 100, message = "la superficie doit être supérieure à 100")
    @Max(value = 1000000, message = "la superficie doit être supérieure à zéro")
    @Positive(message = "La valeur du superficie doit ete positive ")
    private Double superficie;

    @NotNull(message = "La date de création de la ferme ne doit pas être null")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date_de_creation;

    // private List<ChampVM> champVMList;

}
