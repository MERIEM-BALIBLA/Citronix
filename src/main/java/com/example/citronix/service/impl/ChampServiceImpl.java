package com.example.citronix.service.impl;

import com.example.citronix.domain.Arbre;
import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import com.example.citronix.mapper.ChampMapper;
import com.example.citronix.repository.ChampRepository;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.ChampService;
import com.example.citronix.service.DTO.ChampDTO;
import com.example.citronix.service.FermeService;
import com.example.citronix.web.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ChampServiceImpl implements ChampService {

    @Autowired
    private  ChampRepository champRepository;
    @Autowired
    private  ChampMapper champMapper;
    @Autowired
    private  FermeService fermeService;
    @Lazy
    @Autowired
    private  ArbreService arbreService;

//    public ChampServiceImpl(ChampRepository champRepository, ChampMapper champMapper, FermeService fermeService,
//                            ArbreService arbreService) {
//        this.champRepository = champRepository;
//        this.champMapper = champMapper;
//        this.fermeService = fermeService;
//        this.arbreService = arbreService;
//    }

    private boolean champSuperficie(Champ champ) {
        double champSuperficie = champ.getSuperficie();
        double fermeSuperficie = champ.getFerme().getSuperficie();
        return champSuperficie <= 0.5 * fermeSuperficie;
    }

    @Override
    public Optional<Champ> findByNom(String nom) {
        return champRepository.findByNom(nom);
    }

    @Override
    public Optional<Champ> findByFerme(Ferme ferme) {
        return champRepository.findByFerme(ferme);
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
        Optional<Champ> optionalChamp = findByFerme(ferme);
        if (champOptional.isPresent() && optionalChamp.isPresent()) {
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
    public ChampDTO update(UUID id, ChampDTO champDTO) {

//        Verifier si la ferme existe deja
        Optional<Champ> champOptional = findById(id);
        if (champOptional.isEmpty()) {
            throw new FermeUndefinedException("il n'existe pas un champ avec ce ID");
        }

//        verifier s'il existe une ferme avec ce nom
        String ferme_name = champDTO.getFerme();
        Optional<Ferme> fermeOptional = fermeService.findByNom(ferme_name);
        if (fermeOptional.isEmpty()) {
            throw new FermeUndefinedException("Il n'existe pas une ferme avec ce nom");
        }

        Ferme ferme = fermeOptional.get();
        Champ existingChamp = champOptional.get();

//        Verifier s'il existe un champ avec le meme nom et ferme en meme temps
        Optional<Champ> champNom = findByNom(champDTO.getNom());
        Optional<Champ> champFerme = findByFerme(ferme);
        if (champNom.isPresent() && champFerme.isPresent()) {
            throw new ChampAlreadyExistsException("Un champ avec ce nom et ferme existe déjà");
        }

        existingChamp.setFerme(ferme);
        existingChamp.setSuperficie(champDTO.getSuperficie());
        existingChamp.setNom(champDTO.getNom());
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
        Champ champ = champOptional.get();
        List<Arbre> arbres = champ.getArbres();
        if (arbres != null && !arbres.isEmpty()) {
            arbres.forEach(arbre -> arbreService.delete(arbre.getId()));
        }
        champRepository.delete(champ);
    }

    @Override
    public Page<Champ> findAll(Pageable pageable) {
        return champRepository.findAll(pageable);
    }

    @Override
    public void deleteAll(List<Champ> champList) {
        champRepository.deleteAll(champList);
    }
}
