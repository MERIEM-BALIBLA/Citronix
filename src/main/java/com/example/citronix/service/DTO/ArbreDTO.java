package com.example.citronix.service.DTO;

import com.example.citronix.domain.enums.ArbreAge;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
public class ArbreDTO {
    private LocalDateTime date_de_plantation = LocalDateTime.now();
    private ArbreAge age;
    private UUID champ;
    private double productivite_annuelle;
}
