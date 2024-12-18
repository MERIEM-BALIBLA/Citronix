package com.example.citronix.service.DTO;

import jakarta.validation.constraints.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
public class VenteDTO {

    @NotNull(message = "La date de vente ne doit pas être null")
    private LocalDateTime date_de_vente;

    @NotNull(message = "Le prix unitaire ne doit pas être null")
    private Double prix_unitaire;
    @NotNull
    private UUID recolteId;

    @NotNull(message = "Le nom du client ne doit pas être null")
    private String clientName;

    private double prevenu;
}
