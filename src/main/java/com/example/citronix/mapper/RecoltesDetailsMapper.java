package com.example.citronix.mapper;

import com.example.citronix.domain.RecoltesDetails;
import com.example.citronix.service.DTO.RecoltesDetailsDTO;
import com.example.citronix.web.VM.RecoltesDetailsVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecoltesDetailsMapper {

    //    Entity -> DTO
    @Mapping(target = "arbre", source = "arbre.id")
    @Mapping(target = "recolte", source = "recolte.id")
    RecoltesDetailsDTO toDTO(RecoltesDetails recoltesDetails);

    //    DTO -> Entity
//    @Mapping(target = "id", source = "id")
    @Mapping(target = "arbre.id", source = "arbre")
    @Mapping(target = "recolte.id", source = "recolte")
    RecoltesDetails fromDTOtoEntity(RecoltesDetailsDTO recoltesDetailsDTO);

    //    ----------------------------------------------------------------------
    //    VM -> Entity
    @Mapping(target = "arbre.id", source = "arbre")
    @Mapping(target = "recolte.id", source = "recolte")
    RecoltesDetails fromVMtoEntity(RecoltesDetailsVM recoltesDetailsVM);

    //    Entity -> VM
    @Mapping(target = "arbre", source = "arbre.id")
    @Mapping(target = "recolte", source = "recolte.id")
    RecoltesDetailsVM toVM(RecoltesDetails recoltesDetails);

}
