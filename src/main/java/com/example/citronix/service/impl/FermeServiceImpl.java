package com.example.citronix.service.impl;

import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import com.example.citronix.mapper.FermeMapper;
import com.example.citronix.repository.FermeRepository;
import com.example.citronix.service.DTO.FermeDTO;
import com.example.citronix.service.FermeService;
import com.example.citronix.web.errors.FermeAlreadyExistsException;
import com.example.citronix.web.errors.FermeUndefinedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FermeServiceImpl implements FermeService {
    private FermeRepository fermeRepository;
    private FermeMapper fermeMapper;

    public FermeServiceImpl(FermeMapper fermeMapper, FermeRepository fermeRepository) {
        this.fermeMapper = fermeMapper;
        this.fermeRepository = fermeRepository;
    }

    @Override
    public Optional<Ferme> findByNom(String nom) {
        return fermeRepository.findByNom(nom);
    }

    @Override
    public FermeDTO save(FermeDTO fermeDTO) {
//        DTO -> Entity
        Ferme ferme = fermeMapper.toEntity(fermeDTO);

//        Verifier l'existance d'une ferme avce le meme nom
        Optional<Ferme> fermeOptional = findByNom(ferme.getNom());
        if (fermeOptional.isPresent()) {
            throw new FermeAlreadyExistsException("il existe déja une ferme avec ce nom!!");
        }

//        Enregistrer la nouvlle ferme
        Ferme savedFerme = fermeRepository.save(ferme);
        return fermeMapper.toDTO(savedFerme);
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
        fermeRepository.delete(ferme);
    }


    public List<Ferme> getFieldsGraterThan() {
        List<Ferme> fermeList = findAll();

        return fermeList.stream()
                .filter(ferme -> ferme.getChamps().stream()
                        .mapToDouble(Champ::getSuperficie)
                        .sum() < 4000)
                .toList();
    }


}
