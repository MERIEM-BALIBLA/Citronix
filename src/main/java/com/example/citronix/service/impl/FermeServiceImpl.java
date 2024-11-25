package com.example.citronix.service.impl;

import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import com.example.citronix.mapper.FermeMapper;
import com.example.citronix.repository.FermeRepository;
import com.example.citronix.service.DTO.FermeDTO;
import com.example.citronix.service.FermeService;
import com.example.citronix.web.errors.DateOfFermeException;
import com.example.citronix.web.errors.FermeAlreadyExistsException;
import com.example.citronix.web.errors.FermeUndefinedException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FermeServiceImpl implements FermeService {
    @Autowired
    private FermeRepository fermeRepository;

    @Autowired
    private FermeMapper fermeMapper;

    @Lazy
    @Autowired
    private ChampServiceImpl champService;

    @Override
    public Optional<Ferme> findByNom(String nom) {
        return fermeRepository.findByNom(nom);
    }


    @Override
    public Ferme save(Ferme ferme) {

        // Verifier l'existance d'une ferme avce le meme nom
        Optional<Ferme> fermeOptional = findByNom(ferme.getNom());
        if (fermeOptional.isPresent()) {
            throw new FermeAlreadyExistsException("il existe déja une ferme avec ce nom!!");
        }

        // Validation de la date
        if (ferme.getDate_de_creation().isAfter(LocalDateTime.now())) {
            throw new DateOfFermeException("La date de création doit ete au passé");
        }

        // Enregistrer la nouvlle ferme
        return fermeRepository.save(ferme);
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
    @Transactional
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

    @Override
    public Optional<Ferme> findById(UUID id){
        return fermeRepository.findById(id);
    }
}
