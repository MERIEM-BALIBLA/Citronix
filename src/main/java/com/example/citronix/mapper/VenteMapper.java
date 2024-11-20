package com.example.citronix.mapper;

import com.example.citronix.domain.Vente;
import com.example.citronix.service.DTO.VenteDTO;
import com.example.citronix.web.VM.VenteVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VenteMapper {
    @Mapping(target = "recolte.id", source = "recolteId")
    Vente fromDTOtoEntity(VenteDTO venteDTO);

    @Mapping(target = "recolteId", source = "recolte.id")
    VenteDTO toDTO(Vente vente);

    @Mapping(target = "recolte.id", source = "recolteId")

    Vente fromVMtoEntity(VenteVM venteVM);

    @Mapping(target = "recolteId", source = "recolte.id")
    VenteVM toVM(Vente vente);
}
