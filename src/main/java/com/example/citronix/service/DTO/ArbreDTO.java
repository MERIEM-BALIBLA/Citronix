package com.example.citronix.service.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
public class ArbreDTO {
    private UUID id;
    private LocalDateTime date_de_plantation;
    private UUID champ_id;
}
