package com.example.citronix.service.DTO;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
public class RecoltesDetailsDTO {
    private UUID arbre;
    private UUID recolte;
    private double quantite;
}
