package com.example.citronix.service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDateTime;


@Getter
@Setter
@Data
public class FermeDTO {


    private String nom;

    private String localisation;

    private Double superficie;

    private LocalDateTime date_de_creation = LocalDateTime.now();

}
