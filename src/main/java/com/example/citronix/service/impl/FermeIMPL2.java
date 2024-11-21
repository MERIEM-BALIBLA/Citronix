package com.example.citronix.service.impl;

import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import com.example.citronix.mapper.FermeMapper;
import com.example.citronix.repository.FermeRepository;
import com.example.citronix.service.ChampService;
import com.example.citronix.service.DTO.FermeDTO;
import com.example.citronix.service.FermeService;
import com.example.citronix.web.errors.FermeAlreadyExistsException;
import com.example.citronix.web.errors.FermeUndefinedException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FermeIMPL2 implements FermeService {

    private FermeMapper fermeMapper;
    private FermeRepository fermeRepository;
    private ChampService champService;

    public FermeIMPL2(FermeMapper fermeMapper, FermeRepository fermeRepository, ChampService champService) {
        this.fermeRepository = fermeRepository;
        this.fermeMapper = fermeMapper;
        this.champService = champService;
    }

    @Override
    public Optional<Ferme> findByNom(String nom) {
        return fermeRepository.findByNom(nom);
    }

    @Override
    public Ferme save(Ferme ferme) {
        // Check if a Ferme with the same name already exists
        Optional<Ferme> fermeOptional = findByNom(ferme.getNom());
        if (fermeOptional.isPresent()) {
            throw new FermeAlreadyExistsException("Il existe déjà une ferme avec ce nom !");
        }

        // Save the Ferme first (this will not save the Champs yet)
        Ferme savedFerme = fermeRepository.save(ferme);

        // Save each Champ associated with the Ferme
        List<Champ> champList = ferme.getChamps();
        if (champList != null && !champList.isEmpty()) {
            for (Champ champ : champList) {
                champ.setFerme(savedFerme);  // Set the Ferme reference for each Champ
                champService.save(champ);    // Save each Champ
            }
        }

        return savedFerme;
    }

    @Override
    public List<Ferme> findAll() {
        return fermeRepository.findAll();
    }

    @Override
    public FermeDTO update(UUID id, FermeDTO fermeDTO) {
        Ferme ferme = fermeMapper.toEntity(fermeDTO);

        Optional<Ferme> fermeOptional = fermeRepository.findById(id);
        if (fermeOptional.isEmpty()) {
            throw new FermeUndefinedException("Ferme avec ce id n'existe pas verifier l'ID!!");
        }
        Ferme existingFerme = fermeOptional.get();
        existingFerme.setNom(ferme.getNom());
        existingFerme.setLocalisation(ferme.getLocalisation());
        existingFerme.setSuperficie(ferme.getSuperficie());
        existingFerme.setDate_de_creation(ferme.getDate_de_creation());

        fermeRepository.save(existingFerme);
        return fermeMapper.toDTO(existingFerme);
    }

    @Override
    public void delete(UUID id) {
        Optional<Ferme> fermeOptional = fermeRepository.findById(id);
        if (fermeOptional.isEmpty()) {
            throw new FermeUndefinedException("Ferme avec ce id n'existe pas verifier l'ID!!");
        }
        Ferme ferme = fermeOptional.get();
        List<Champ> champList = ferme.getChamps();

        if (champList != null && !champList.isEmpty()) {
            champList.forEach(champ -> champService.delete(champ.getId()));
        }

        fermeRepository.delete(ferme);
    }


    @Override
    public Ferme getFermeDatails(String nom) {
        Optional<Ferme> optionalFerme = findByNom(nom);
        if (optionalFerme.isEmpty()) {
            throw new FermeUndefinedException("Il n'existe pas une ferme avec ce nom");
        }
        return optionalFerme.get();
    }

    @Override
    public Ferme search(String name, String location, Double area) {

        if (name == null && location == null && area == null) {
            throw new IllegalArgumentException("At least one search criteria must be provided.");
        }

        Ferme exampleFerme = Ferme.builder()
                .nom(name)
                .localisation(location)
                .superficie(area)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnoreNullValues()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("location", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("area", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("createdAt", ExampleMatcher.GenericPropertyMatchers.exact());


        Example<Ferme> example = Example.of(exampleFerme, matcher);


        return fermeRepository.findOne(example)
                .orElseThrow(() -> new FermeUndefinedException("No farm found with the given criteria"));
    }
}
