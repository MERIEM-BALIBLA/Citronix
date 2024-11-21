package com.example.citronix.mapper;

import com.example.citronix.domain.Arbre;
import com.example.citronix.service.DTO.ArbreDTO;
import com.example.citronix.web.VM.ArbreVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArbreMapper {

    //    DTO -> Entity
    @Mapping(target = "champ.id", source = "champ_id")
    Arbre toEntityFromDTO(ArbreDTO arbreDTO);

    //    Entity -> DTO
    @Mapping(target = "champ_id", source = "champ.id")
    ArbreDTO toDTO(Arbre arbre);
//    -------------------------------------------------------

    //    VM -> Entity
//    @Mapping(target = "date_de_plantation", source = "date_de_plantation")
    @Mapping(target = "champ.id", source = "champ_id")
    Arbre toEntityFromVM(ArbreVM arbreVM);

    //   Entity -> VM
//    @Mapping(target = "date_de_plantation", source = "date_de_plantation")
    @Mapping(target = "champ_id", source = "champ.id")
    ArbreVM toVM(Arbre arbre);

    //    -----------------------------------------------------
}
