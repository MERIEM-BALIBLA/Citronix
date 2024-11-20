package com.example.citronix.mapper;

import com.example.citronix.domain.Recolte;
import com.example.citronix.service.DTO.RecolteDTO;
import com.example.citronix.web.VM.RecolteVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecolteMapper {

    //    DTO -> Entity
    Recolte toEntityFromDTO(RecolteDTO recolteDTO);

    //    Entity -> DTO
    RecolteDTO toDTO(Recolte recolte);

//    -----------------------------------------------

    //    VM -> Entity
    Recolte toEntityFromVM(RecolteVM recolteVM);

    //    Entity -> VM
    RecolteVM toVM(Recolte recolte);

}
