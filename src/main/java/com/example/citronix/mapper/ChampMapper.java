package com.example.citronix.mapper;

import com.example.citronix.domain.Champ;
import com.example.citronix.service.DTO.ChampDTO;
import com.example.citronix.web.VM.ChampVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChampMapper {
    // Entity -> DTO
//    ChampDTO toDTO(Champ champ);

    // DTO -> Entity
    @Mapping(target = "ferme.nom", source = "ferme")
    @Mapping(target = "nom", source = "nom")
    Champ toEntity(ChampDTO champDTO);

    List<Champ> toEntity(List<ChampDTO> champDTOS);

    // Entity -> VM
    @Mapping(target = "ferme", source = "ferme.nom")
    @Mapping(target = "nom", source = "nom")
//    @Mapping(target = "superficie", source = "superficie")
    ChampVM toVM(Champ champ);

    // VM -> Entity
    @Mapping(target = "ferme.nom", source = "ferme")
    @Mapping(target = "nom", source = "nom")
//    @Mapping(target = "superficie", source = "superficie")
    Champ toEntity(ChampVM champVM);

    @Mapping(target = "ferme", source = "ferme.nom")
    @Mapping(target = "nom", source = "nom")
    ChampDTO toDTO(Champ champ);

}
