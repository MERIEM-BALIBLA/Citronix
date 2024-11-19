package com.example.citronix.mapper;

import com.example.citronix.domain.Champ;
import com.example.citronix.service.DTO.ChampDTO;
import com.example.citronix.web.VM.ChampVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

//@Mapper(componentModel = "spring")
//public interface ChampMapper {
//    // Entity -> DTO
//    ChampDTO toDTO(Champ champ);
//
//    // DTO -> Entity
//    Champ toEntity(ChampDTO champDTO);
//
//    // Entity -> VM
//    @Mapping(target = "ferme_nom", source = "ferme.nom")
//    @Mapping(target = "superficie", source = "superficie")
//    ChampVM toVM(Champ champ);
//
//    // VM -> Entity
//    @Mapping(target = "ferme.nom", source = "ferme_nom")
//    @Mapping(target = "superficie", source = "superficie")
//    Champ toEntity(ChampVM champVM);
//}
@Mapper(componentModel = "spring")
public interface ChampMapper {
    // Entity -> DTO
//    ChampDTO toDTO(Champ champ);

    // DTO -> Entity
    Champ toEntity(ChampDTO champDTO);

    List<Champ> toEntity(List<ChampDTO> champDTOS);

    // Entity -> VM
    @Mapping(target = "ferme_nom", source = "ferme.nom")
    @Mapping(target = "superficie", source = "superficie")
    ChampVM toVM(Champ champ);

    // VM -> Entity
    @Mapping(target = "ferme.nom", source = "ferme_nom")
    @Mapping(target = "superficie", source = "superficie")
    Champ toEntity(ChampVM champVM);


    default ChampDTO toDTO(Champ champ) {
        ChampDTO dto = new ChampDTO();
        dto.setFerme_nom(champ.getFerme().getNom()); // Explicitly set ferme_nom from ferme
        dto.setSuperficie(champ.getSuperficie());
        return dto;
    }
}
