package com.example.citronix.service.impl;

import com.example.citronix.domain.Arbre;
import com.example.citronix.domain.Champ;
import com.example.citronix.mapper.ArbreMapper;
import com.example.citronix.repository.ArbreRepository;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.ChampService;
import com.example.citronix.service.DTO.ArbreDTO;
import com.example.citronix.web.errors.ArbreUndefinedException;
import com.example.citronix.web.errors.ChampUndefinedException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ArbreServiceImpl implements ArbreService {
    private final ArbreRepository arbreRepository;
    private final ArbreMapper arbreMapper;
    private final ChampService champService;

    public ArbreServiceImpl(ArbreRepository arbreRepository, ArbreMapper arbreMapper, ChampService champService) {
        this.arbreRepository = arbreRepository;
        this.arbreMapper = arbreMapper;
        this.champService = champService;
    }

    @Override
    public ArbreDTO save(ArbreDTO arbreDTO) {
        Optional<Champ> champ = champService.findById(arbreDTO.getChamp_id());
        if (champ.isEmpty()) {
            throw new ChampUndefinedException("Champ non trouvable");
        }
        Arbre arbre = arbreMapper.toEntityFromDTO(arbreDTO);
        Arbre savedArbre = arbreRepository.save(arbre);
        return arbreMapper.toDTO(savedArbre);
    }

    @Override
    public Optional<Arbre> findById(UUID id) {
        return arbreRepository.findById(id);
    }

    @Override
    public ArbreDTO update(UUID id, ArbreDTO arbreDTO) {

        Optional<Arbre> optionalArbre = findById(id);
        if (optionalArbre.isEmpty()) {
            throw new ArbreUndefinedException("il n'existe pas une arbre avec ce ID");
        }

        Optional<Champ> champ = champService.findById(arbreDTO.getChamp_id());
        if (champ.isEmpty()) {
            throw new ChampUndefinedException("Champ non trouvable");
        }

        Arbre existingArbre = optionalArbre.get();
        Arbre arbre = arbreMapper.toEntityFromDTO(arbreDTO);

        existingArbre.setChamp(arbre.getChamp());
        existingArbre.setDate_de_plantation(arbre.getDate_de_plantation());
        Arbre updatedArbre = arbreRepository.save(existingArbre);
        return arbreMapper.toDTO(updatedArbre);
    }

    @Override
    public void delete(UUID id) {
        Optional<Arbre> optionalArbre = findById(id);
        if (optionalArbre.isEmpty()) {
            throw new ArbreUndefinedException("il n'existe pas une arbre avec ce ID");
        }
        arbreRepository.delete(optionalArbre.get());
    }

//    public Page<ArbreDTO> getAllProducts(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "true") boolean ascending
//    ) {
//        // Définir l'ordre de tri
//        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        // Récupérer la page des entités Recolte
//        Page<Arbre> recoltePage = arbreRepository.findAll(pageable);
//
//        // Convertir la page de Recolte en page de RecolteDTO
//        Page<RecolteDTO> recolteDTOPage = recoltePage.map(recolte -> modelMapper.map(recolte, RecolteDTO.class));
//
//        return recolteDTOPage;
//
//    }
}
