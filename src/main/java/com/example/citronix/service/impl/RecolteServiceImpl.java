package com.example.citronix.service.impl;

import com.example.citronix.domain.Arbre;
import com.example.citronix.domain.Recolte;
import com.example.citronix.domain.RecoltesDetails;
import com.example.citronix.mapper.RecolteMapper;
import com.example.citronix.repository.RecolteRepository;
import com.example.citronix.repository.RecoltesDetailsRepository;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.DTO.RecolteDTO;
import com.example.citronix.service.RecolteService;
import com.example.citronix.validation.RecolteValidation;
import com.example.citronix.web.VM.RecolteVM;
import com.example.citronix.web.errors.RecolteUndefinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RecolteServiceImpl implements RecolteService {

    @Autowired
    private  RecolteRepository recolteRepository;
    @Autowired
    private  RecolteMapper recolteMapper;
    @Autowired
    private  RecoltesDetailsRepository recoltesDetailsRepository;
    @Autowired
    private  ArbreService arbreService;
    @Autowired
    private  RecolteValidation recolteValidation;

//    public RecolteServiceImpl(RecolteRepository recolteRepository, RecolteMapper recolteMapper, RecoltesDetailsRepository recoltesDetailsRepository, ArbreService arbreService, RecolteValidation recolteValidation) {
//        this.recolteRepository = recolteRepository;
//        this.recolteMapper = recolteMapper;
//        this.recoltesDetailsRepository = recoltesDetailsRepository;
//        this.arbreService = arbreService;
//        this.recolteValidation = recolteValidation;
//    }

    @Override
    public Recolte save(RecolteVM recolteVM) {
        Recolte recolte = recolteMapper.toEntityFromVM(recolteVM);

        recolteValidation.seuleRecolteParSaison(recolte);
        Recolte savedRecolte = recolteRepository.save(recolte);

        if (recolteVM.getRecoltesDetailsVMS() != null && !recolteVM.getRecoltesDetailsVMS().isEmpty()) {
            List<RecoltesDetails> recoltesDetails = recolteVM.getRecoltesDetailsVMS().stream()
                    .map(detailVM -> {
                        RecoltesDetails detail = new RecoltesDetails();
                        Arbre arbre = arbreService.findById(detailVM.getArbre()).get();
                        detail.setArbre(arbre);
                        detail.setQuantite(detailVM.getQuantite());
                        detail.setRecolte(savedRecolte);
                        return detail;
                    })
                    .collect(Collectors.toList());

            recoltesDetailsRepository.saveAll(recoltesDetails);
            savedRecolte.setRecoltesDetails(recoltesDetails);
        }

        return savedRecolte;
    }
/*
    public RecolteDTO save(RecolteDTO recolteDTO) {
        // Check for null or invalid fields
        if (recolteDTO.getSaison() == null || recolteDTO.getDate_de_recolte() == null || recolteDTO.getQuatiteTotale() == null) {
            throw new InvalidRecolteException("Tous les champs obligatoires doivent être fournis");
        }


        // Transform DTO -> Entity
        Recolte recolte = recolteMapper.toEntityFromDTO(recolteDTO);

        // Check for existing recolte with the same `saison` and `date_de_recolte`
        Optional<Recolte> exists = recolteRepository.findBySaisonAndDateDeRecolte(recolteDTO.getSaison(), recolte.getDate_de_recolte());
        if (exists.isPresent()) {
            throw new DuplicateRecolteException("Une récolte avec cette saison et cette date existe déjà");
        }

        // Save the entity
        Recolte savedRecolte = recolteRepository.save(recolte);

        // Transform Entity -> DTO and return
        return recolteMapper.toDTO(savedRecolte);
    }
*/

    @Override
    public Optional<Recolte> findById(UUID id) {
        return recolteRepository.findById(id);
    }

    @Override
    public RecolteDTO update(UUID id, RecolteDTO recolteDTO) {

        Optional<Recolte> optionalRecolte = findById(id);
        if (optionalRecolte.isEmpty()) {
            throw new RecolteUndefinedException("Il n'existe pas un recolte avec ce ID");
        }

        Recolte existingRecolte = optionalRecolte.get();
        Recolte recolte = recolteMapper.toEntityFromDTO(recolteDTO);

        existingRecolte.setDate_de_recolte(recolte.getDate_de_recolte());
        existingRecolte.setSaison(recolte.getSaison());
        existingRecolte.setQuatiteTotale(recolte.getQuatiteTotale());

        Recolte updatedRecolte = recolteRepository.save(existingRecolte);
        return recolteMapper.toDTO(updatedRecolte);
    }

    @Override
    public void delete(UUID id) {

        Optional<Recolte> optionalRecolte = findById(id);
        if (optionalRecolte.isEmpty()) {
            throw new RecolteUndefinedException("Ce recolte n'existe pas");
        }
        Recolte recolte = optionalRecolte.get();

        if (recolte.getRecoltesDetails() != null && !recolte.getRecoltesDetails().isEmpty()) {
            recoltesDetailsRepository.deleteAll(recolte.getRecoltesDetails());
        }

        recolteRepository.delete(recolte);
    }

    @Override
    public Page<Recolte> findAll(Pageable pageable) {
        return recolteRepository.findAll(pageable);
    }

}