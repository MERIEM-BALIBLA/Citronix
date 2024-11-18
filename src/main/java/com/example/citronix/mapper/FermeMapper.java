package com.example.citronix.mapper;

import com.example.citronix.domain.Ferme;
import com.example.citronix.service.DTO.FermeDTO;
import com.example.citronix.web.VM.FermeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FermeMapper {

    // Map Entity to DTO
    FermeDTO toDTO(Ferme ferme);

    // Map DTO to Entity
    Ferme toEntity(FermeDTO fermeDTO);

    // Map Entity to VM (for display purposes)
    @Mapping(target = "nom", source = "nom")
    @Mapping(target = "localisation", source = "localisation")
    @Mapping(target = "superficie", source = "superficie")
    @Mapping(target = "date_de_creation", source = "date_de_creation")
    FermeVM toVM(Ferme ferme);

    List<FermeVM> toVMs(List<Ferme> fermes);

    // Map VM to Entity (for saving or updating)
    @Mapping(target = "nom", source = "nom")
    @Mapping(target = "localisation", source = "localisation")
    @Mapping(target = "superficie", source = "superficie")
    @Mapping(target = "date_de_creation", expression = "java(java.time.LocalDateTime.now())")
    // Set creation date
    Ferme toEntity(FermeVM fermeVM);
}
