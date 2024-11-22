package com.example.citronix.web.VM;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
public class VenteVM {

    @NotNull(message = "date de vente null")
    private LocalDateTime date_de_vente;

    @NotNull(message = "prix unitaire null")
    private Double prix_unitaire;

    @NotNull(message = "recolteId null")
    private UUID recolteId;

    @NotNull(message = "clientName null")
    @NotBlank(message = "empty date de clientName")
    private String clientName;

    private double prevenu;

}
