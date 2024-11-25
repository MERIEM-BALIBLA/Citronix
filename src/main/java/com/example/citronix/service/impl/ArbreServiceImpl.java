package com.example.citronix.service.impl;

import com.example.citronix.domain.Arbre;
import com.example.citronix.domain.Champ;
import com.example.citronix.domain.RecoltesDetails;
import com.example.citronix.mapper.ArbreMapper;
import com.example.citronix.repository.ArbreRepository;
import com.example.citronix.repository.RecoltesDetailsRepository;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.ChampService;
import com.example.citronix.service.DTO.ArbreDTO;
import com.example.citronix.service.RecoltesDetailsService;
import com.example.citronix.web.errors.ArbrePlantationMonthException;
import com.example.citronix.web.errors.ArbreUndefinedException;
import com.example.citronix.web.errors.ChampUndefinedException;
import com.example.citronix.web.errors.MaximumArbreForFermeException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ArbreServiceImpl implements ArbreService {
    @Autowired
    private ArbreRepository arbreRepository;
    @Autowired
    private ArbreMapper arbreMapper;
    @Autowired
    private ChampService champService;
    @Autowired
    private RecoltesDetailsRepository recoltesDetailsRepository;

    @Lazy
    @Autowired
    private RecoltesDetailsService recoltesDetailsService;


    @Override
    public ArbreDTO save(ArbreDTO arbreDTO) {
        Optional<Champ> optionalChamp = champService.findById(arbreDTO.getChamp_id());
        if (optionalChamp.isEmpty()) {
            throw new ChampUndefinedException("Champ non trouvable");
        }
        Champ champ = optionalChamp.get();

        double maxArbrePourChamp = champ.getSuperficie() / 100;
        if (champ.getArbres().size() + 1 > maxArbrePourChamp) {
            throw new MaximumArbreForFermeException("Le champ a atteint le nombre maximal d'arbres autorisés.");
        }


        Arbre arbre = arbreMapper.toEntityFromDTO(arbreDTO);
        Month month = arbre.getDate_de_plantation().getMonth();
        if (month != Month.MARCH && month != Month.APRIL && month != Month.MAY) {
            throw new ArbrePlantationMonthException("Les arbres ne peuvent être plantés qu'entre de mars a mai.");
        }

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

    @Transactional
    @Override
    public void delete(UUID id) {
        Optional<Arbre> optionalArbre = findById(id);
        if (optionalArbre.isEmpty()) {
            throw new ArbreUndefinedException("Il n'existe pas un arbre avec ce ID");
        }
        Arbre arbre = optionalArbre.get();

        // Supprimer les RecoltesDetails associés à cet arbre
        List<RecoltesDetails> recoltesDetailsList = arbre.getRecoltesDetailsList();
        if (recoltesDetailsList != null && !recoltesDetailsList.isEmpty()) {
            recoltesDetailsList.forEach(recoltesDetails -> recoltesDetailsService.delete(recoltesDetails.getId()));
        }

        // Supprimer l'Arbre
        arbreRepository.delete(arbre);
    }


    @Override
    public Page<Arbre> findAll(Pageable pageable) {
        return arbreRepository.findAll(pageable);
    }
}
