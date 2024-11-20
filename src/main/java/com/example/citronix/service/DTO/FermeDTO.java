package com.example.citronix.service.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
public class FermeDTO {


    private String nom;

    private String localisation;

    private Double superficie;

    private LocalDateTime date_de_creation = LocalDateTime.now();


//    --------------------------------------------------------------

//    private List<ChampDTO> champDTOList;

}
