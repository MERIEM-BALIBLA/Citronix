//package com.example.citronix.service.impl;
//
//import com.example.citronix.domain.Champ;
//import com.example.citronix.domain.Ferme;
//import com.example.citronix.mapper.ChampMapper;
//import com.example.citronix.mapper.FermeMapper;
//import com.example.citronix.repository.FermeRepository;
//import com.example.citronix.service.ChampService;
//import com.example.citronix.service.DTO.FermeDTO;
//import com.example.citronix.service.FermeService;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//public class FermeIMPL2 implements FermeService {
//
//    private FermeMapper fermeMapper;
//    private FermeRepository fermeRepository;
//    private ChampService champService;
//    private ChampMapper champMapper;
//
//    public FermeIMPL2(FermeMapper fermeMapper, FermeRepository fermeRepository, ChampService champService, ChampMapper champMapper) {
//        this.fermeRepository = fermeRepository;
//        this.fermeMapper = fermeMapper;
//        this.champService = champService;
//        this.champMapper = champMapper;
//    }
//
//    @Override
//    public Optional<Ferme> findByNom(String nom) {
//        return Optional.empty();
//    }
//
//    @Override
////    public FermeDTO save(FermeDTO fermeDTO) {
////        List<ChampDTO> champDTOList = fermeDTO.getChampDTOList();
////        for (ChampDTO champDTO : champDTOList) {
////            champService.save(champDTO);
////        }
////        fermeDTO.setChampDTOList(champDTOList);
////        Ferme ferme = fermeMapper.toEntity(fermeDTO);
////        Ferme savedFerme = fermeRepository.save(ferme);
////        return fermeMapper.toDTO(savedFerme);
////    }
//
//    public FermeDTO save(FermeDTO fermeDTO) {
//        Ferme ferme = fermeMapper.toEntity(fermeDTO);
//
//        List<Champ> champs = fermeDTO.getChampDTOList()
//                .stream()
//                .map(champMapper::toEntity)
//                .collect(Collectors.toList());
//        champs.forEach(champ -> champ.setFerme(ferme));
//        ferme.setChamps(champs);
//
//        Ferme savedFerme = fermeRepository.save(ferme);
//        return fermeMapper.toDTO(savedFerme);
//    }
//
//
//    @Override
//    public List<Ferme> findAll() {
//        return null;
//    }
//
//    @Override
//    public FermeDTO update(UUID id, FermeDTO fermeDTO) {
//        return null;
//    }
//
//    @Override
//    public void delete(UUID id) {
//
//    }
//}
