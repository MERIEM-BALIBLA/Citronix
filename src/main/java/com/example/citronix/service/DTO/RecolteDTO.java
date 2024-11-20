package com.example.citronix.service.DTO;

import com.example.citronix.domain.enums.Saison;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class RecolteDTO {
    private Saison saison;
    private LocalDateTime date_de_recolte;
    private Double quatiteTotale;
}
