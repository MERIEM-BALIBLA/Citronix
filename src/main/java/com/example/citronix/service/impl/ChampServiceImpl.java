package com.example.citronix.service.impl;

import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import com.example.citronix.mapper.ChampMapper;
import com.example.citronix.repository.ChampRepository;
import com.example.citronix.service.ChampService;
import com.example.citronix.service.DTO.ChampDTO;
import com.example.citronix.service.FermeService;
import com.example.citronix.web.errors.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ChampServiceImpl implements ChampService {

    private final ChampRepository champRepository;
    private final ChampMapper champMapper;
    private final FermeService fermeService;

    public ChampServiceImpl(ChampRepository champRepository, ChampMapper champMapper, FermeService fermeService) {
        this.champRepository = champRepository;
        this.champMapper = champMapper;
        this.fermeService = fermeService;
    }

    private boolean champSuperficie(Champ champ) {
        double champSuperficie = champ.getSuperficie();
        double fermeSuperficie = champ.getFerme().getSuperficie();
        return champSuperficie <= 0.5 * fermeSuperficie;
    }

    @Override
    public Optional<Champ> findByNom(String nom) {
        return champRepository.findByNom(nom);
    }

    private boolean isSuperficieExceedingLimit(Champ newChamp) {
        Ferme ferme = newChamp.getFerme();

        double fermeSuperficie = ferme.getSuperficie();

        List<Champ> champs = ferme.getChamps();

        double totalExistingSuperficie = champs.stream()
                .mapToDouble(Champ::getSuperficie)
                .sum();

        double totalSuperficie = totalExistingSuperficie + newChamp.getSuperficie();
        return totalSuperficie >= fermeSuperficie;
    }

    @Override
    public ChampDTO save(ChampDTO champDTO) {
        // Verifier si la ferme existe
        String ferme_name = champDTO.getFerme();
        Optional<Ferme> fermeOptional = fermeService.findByNom(ferme_name);
        if (fermeOptional.isEmpty()) {
            throw new FermeUndefinedException("Il n'existe pas une ferme avec ce nom");
        }

        Ferme ferme = fermeOptional.get();

        // Verifier l'existence d'un champ avec le même nom
        Optional<Champ> champOptional = findByNom(champDTO.getNom());
        if (champOptional.isPresent()) {
            throw new ChampAlreadyExistsException("Un champ avec ce nom existe déjà");
        }

        int champCount = champRepository.countByFerme(ferme);
        if (champCount >= 10) {
            throw new TooManyChampsException("Une ferme ne peut pas avoir plus de 10 champs");
        }

        // DTO -> Entity
        Champ champ = champMapper.toEntity(champDTO);
        champ.setFerme(ferme);

        if (isSuperficieExceedingLimit(champ)) {
            throw new SuperficieException("La somme des superficies des champs doit être inférieure à la superficie de la ferme");
        }

        // Save the Champ
        Champ champSaved = champRepository.save(champ);
        return champMapper.toDTO(champSaved);
    }

/*
    public ChampDTO save(ChampDTO champDTO) {
        // Verifier si la ferme existe
        String ferme_name = champDTO.getFerme();
        Optional<Ferme> fermeOptional = fermeService.findByNom(ferme_name);
        if (fermeOptional.isEmpty()) {
            throw new FermeUndefinedException("il n'exite pas une ferme avec ce nom");
        }

        Ferme ferme = fermeOptional.get();

        // Verifier l'existance d'un champ avce meme le nom
        Optional<Champ> champOptional = findByNom(champDTO.getNom());
        if (champOptional.isPresent()) {
            throw new ChampAlreadyExistsException("Un champ avec ce nom existe déjà");
        }

        int champCount = champRepository.countByFerme(ferme);
        if (champCount >= 10) {
            throw new TooManyChampsException("Une ferme ne peut pas avoir plus de 10 champs");
        }

        // DTO -> Entity
        Champ champ = champMapper.toEntity(champDTO);

        champ.setFerme(fermeOptional.get());
        if (!champSuperficie(champ)) {
            throw new ChampMustUnderException("La superficier du champ ne doit pas prendre plus de 50% du ferme");
        }

        boolean status = fermeService.verifierSuperficieDeFerme(ferme);
        if (!status) {
            throw new SuperficieException("La somme des superficies des champs doit etre mois de la superfifice de la ferme");
        }

        Champ champSaved = champRepository.save(champ);
        return champMapper.toDTO(champSaved);
    }
*/

    @Override
    public Optional<Champ> findById(UUID id) {
        return champRepository.findById(id);
    }

    @Override
    /*public ChampDTO update(UUID id, ChampDTO champDTO) {
        Champ champ = champMapper.toEntity(champDTO);

        Optional<Champ> champOptional = findById(id);
        if (champOptional.isEmpty()) {
            throw new FermeUndefinedException("il n'exite pas une ferme avec ce ID");
        }

        Champ existingChamp = champOptional.get();

        existingChamp.setFerme(champ.getFerme());
        existingChamp.setSuperficie(champ.getSuperficie());
        champRepository.save(existingChamp);

        return champMapper.toDTO(existingChamp);
    }*/

    public ChampDTO update(UUID id, ChampDTO champDTO) {
        // Verifier l'existance d'un champ avec ce ID
        Optional<Champ> champOptional = findById(id);
        if (champOptional.isEmpty()) {
            throw new FermeUndefinedException("il n'existe pas un champ avec ce ID");
        }

        Champ existingChamp = champOptional.get();

        // Verifier s'il existe une ferme avce ce nom
        if (champDTO.getFerme() != null) {
            Ferme existingFerme = fermeService.findByNom(champDTO.getFerme())
                    .orElseThrow(() -> new FermeUndefinedException("La ferme avec ce nom n'existe pas"));

            existingChamp.setFerme(existingFerme);
        }

        existingChamp.setSuperficie(champDTO.getSuperficie());
        champRepository.save(existingChamp);
        // Entity -> DTO
        return champMapper.toDTO(existingChamp);
    }

    @Override
    public void delete(UUID id) {
        Optional<Champ> champOptional = findById(id);
        if (champOptional.isEmpty()) {
            throw new ChampUndefinedException("il n'existe pas un champ avec ce ID");
        }
        champRepository.delete(champOptional.get());
    }

    @Override
    public Page<Champ> findAll(Pageable pageable) {
        return champRepository.findAll(pageable);
    }

}
