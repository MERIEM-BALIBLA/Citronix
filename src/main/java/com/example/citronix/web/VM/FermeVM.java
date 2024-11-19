package com.example.citronix.web.VM;

import com.example.citronix.service.DTO.ChampDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Max(value = 10000, message = "la superficie doit être supérieure à zéro")
    private Double superficie;

    private LocalDateTime date_de_creation = LocalDateTime.now();

    //    -------------------------------------------------------
    private List<ChampVM> champVMList;

}
