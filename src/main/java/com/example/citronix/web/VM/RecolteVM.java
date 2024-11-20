package com.example.citronix.web.VM;

import com.example.citronix.domain.enums.Saison;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class RecolteVM {

    @NotNull(message = "La saison est obligatoire")
    private Saison saison;

    @NotNull(message = "La date de récolte est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date_de_recolte;

    @NotNull(message = "La quantité totale est obligatoire")
    @Positive(message = "La quantité totale doit être supérieure à 0")
    private Double quatiteTotale;


}
