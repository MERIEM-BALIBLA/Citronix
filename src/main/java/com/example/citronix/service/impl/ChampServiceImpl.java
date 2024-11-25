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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ChampServiceImpl implements ChampService {

    @Autowired
    private ChampRepository champRepository;
    @Autowired
    private ChampMapper champMapper;
    @Autowired
    private FermeService fermeService;
    @Lazy
    @Autowired
    private ArbreService arbreService;


    @Override
    public Optional<Champ> findByNom(String nom) {
        return champRepository.findByNom(nom);
    }


    public boolean isSuperficieExceedingLimit(Champ newChamp) {
        Ferme ferme = newChamp.getFerme();

        double fermeSuperficie = ferme.getSuperficie();

//        List<Champ> champs = ferme.getChamps();
        // Ensure champs list is not null
        List<Champ> champs = ferme.getChamps();
        if (champs == null) {
            champs = new ArrayList<>();
        }

        double totalExistingSuperficie = champs.stream()
                .mapToDouble(Champ::getSuperficie)
                .sum();

        double totalSuperficie = totalExistingSuperficie + newChamp.getSuperficie();
        return totalSuperficie >= fermeSuperficie;
    }

    public boolean champSuperficie(Champ champ, Ferme ferme) {
        double champSuperficie = champ.getSuperficie();
        double fermeSuperficie = ferme.getSuperficie();
        return champSuperficie >= 0.5 * fermeSuperficie;
    }

    public void validateChampName(String champNom) {
        if (findByNom(champNom).isPresent()) {
            throw new ChampAlreadyExistsException("Un champ avec ce nom existe déjà");
        }
    }

    public void validateChampCount(Ferme ferme) {
        int champCount = countByFerme(ferme);
        if (champCount >= 10) {
            throw new TooManyChampsException("Une ferme ne peut pas avoir plus de 10 champs");
        }
    }

    public void validateChampSuperficie(Champ champ, Ferme ferme) {
        if (champSuperficie(champ, ferme)) {
            throw new ChampMustUnderException("La superficie du champ ne doit pas prendre plus de 50% du ferme");
        }
    }

    public void validateMinimumSuperficie(Champ champ) {
        if (champ.getSuperficie() < 1000) {
            throw new SuperficieException("La superficie du champ doit etre soit 1000 ou plus");
        }
    }

    public void validateTotalSuperficie(Champ champ) {
        if (isSuperficieExceedingLimit(champ)) {
            throw new SuperficieException(
                    "La somme des superficies des champs doit être inférieure à la superficie de la ferme"
            );
        }
    }

    @Override
    public Champ save(Champ champ) {
        // Verifier si la ferme existe
        Ferme champFerme = champ.getFerme();
        Optional<Ferme> fermeOptional = fermeService.findByNom(champFerme.getNom());
        if (fermeOptional.isEmpty()) {
            throw new FermeUndefinedException("Il n'existe pas une ferme avec ce nom");
        }

        Ferme ferme = fermeOptional.get();

        // Verifier l'existence d'un champ avec le même nom
        validateChampName(champ.getNom());

        // Verifier si Ferme contient plus de 10 champs
        validateChampCount(ferme);

        // Verifier si superficie du champ prend 50% de la ferme
        validateChampSuperficie(champ, ferme);

        validateMinimumSuperficie(champ);
        champ.setFerme(ferme);

        // Verifier si la somme des superficie des champs > a la superficie de l'arbre
        validateTotalSuperficie(champ);

        return champRepository.save(champ);
    }

    @Override
    public Optional<Champ> findById(UUID id) {
        return champRepository.findById(id);
    }

    @Override
    public ChampDTO update(UUID id, ChampDTO champDTO) {

        // Verifier si la ferme existe deja
        Optional<Champ> champOptional = findById(id);
        if (champOptional.isEmpty()) {
            throw new FermeUndefinedException("il n'existe pas un champ avec ce ID");
        }

        // verifier s'il existe une ferme avec ce nom
        String ferme_name = champDTO.getFerme();
        Optional<Ferme> fermeOptional = fermeService.findByNom(ferme_name);
        if (fermeOptional.isEmpty()) {
            throw new FermeUndefinedException("Il n'existe pas une ferme avec ce nom");
        }

        Ferme ferme = fermeOptional.get();
        Champ existingChamp = champOptional.get();

        // Verifier s'il existe un champ avec le meme nom
        Optional<Champ> champNom = findByNom(champDTO.getNom());
        if (champNom.isPresent()) {
            throw new ChampAlreadyExistsException("Un champ avec ce nom et ferme existe déjà");
        }

        existingChamp.setFerme(ferme);
        existingChamp.setSuperficie(champDTO.getSuperficie());
        existingChamp.setNom(champDTO.getNom());

        champRepository.save(existingChamp);
        return champMapper.toDTO(existingChamp);
    }

    @Override
    @Transactional
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

    public int countByFerme(Ferme ferme) {
        return champRepository.countByFerme(ferme);
    }

}


