//package com.example.citronix.mapper;
//
//import com.example.citronix.domain.Arbre;
//import com.example.citronix.service.DTO.ArbreDTO;
//import com.example.citronix.web.VM.ArbreVM;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring")
//public interface ArbreMapper {
//
//    //    DTO -> Arbre
//    Arbre toEntity(ArbreDTO arbreDTO);
//
//    //    Arbre -> DTO
//    default ArbreDTO toDTO(Arbre arbre) {
//        ArbreDTO dto = new ArbreDTO();
//        dto.setDate_de_plantation(arbre.getDate_de_plantation());
//        dto.setAge(arbre.getAge());
//        dto.setChamp(arbre.getChamp().getId());
//        dto.setProductivite_annuelle(arbre.getProductivite_annuelle());
//        return dto;
//    }
//
////    -----------------------------------------------------------
////    Arbre -> VM
//
//    @Mapping(target = "date_de_plantation", source = "date_de_plantation")
//    @Mapping(target = "age", source = "age")
//    @Mapping(target = "champ", source = "champ.id")
//    @Mapping(target = "productivite_annuelle", source = "productivite_annuelle")
//    ArbreVM toVM(Arbre arbre);
//
//    //  VM -> Arbre
//    @Mapping(target = "date_de_plantation", source = "date_de_plantation")
//    @Mapping(target = "age", source = "age")
//    @Mapping(target = "champ.id", source = "champ")
//    @Mapping(target = "productivite_annuelle", source = "productivite_annuelle")
//    Arbre toEntity(ArbreVM arbreVM);
//
//}
