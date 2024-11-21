package com.example.citronix.mapper;

import com.example.citronix.domain.Ferme;
import com.example.citronix.service.DTO.FermeDTO;
import com.example.citronix.web.VM.FermeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FermeMapper {

    FermeDTO toDTO(Ferme ferme);

    Ferme toEntity(FermeDTO fermeDTO);

    FermeVM toVM(Ferme ferme);

    List<FermeVM> toVMs(List<Ferme> fermes);

    Ferme toEntity(FermeVM fermeVM);
}
