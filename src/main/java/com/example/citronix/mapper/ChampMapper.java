package com.example.citronix.mapper;

import com.example.citronix.domain.Champ;
import com.example.citronix.service.DTO.ChampDTO;
import com.example.citronix.web.VM.ChampVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChampMapper {

    // DTO -> Entity
    @Mapping(target = "ferme.nom", source = "ferme")
//    @Mapping(target = "nom", source = "nom")
    Champ toEntity(ChampDTO champDTO);

    List<Champ> toEntity(List<ChampDTO> champDTOS);

    // Entity -> VM
    @Mapping(target = "ferme", source = "ferme.nom")
//    @Mapping(target = "nom", source = "nom")
    ChampVM toVM(Champ champ);

    @Mapping(target = "ferme.nom", source = "ferme")
//    @Mapping(target = "nom", source = "nom")
    Champ toEntity(ChampVM champVM);

    @Mapping(target = "ferme", source = "ferme.nom")
//    @Mapping(target = "nom", source = "nom")
    ChampDTO toDTO(Champ champ);

}
