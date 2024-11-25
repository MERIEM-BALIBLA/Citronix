package com.example.citronix.service.impl;

import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Recolte;
import com.example.citronix.domain.RecoltesDetails;
import com.example.citronix.domain.enums.Saison;
import com.example.citronix.mapper.RecolteMapper;
import com.example.citronix.repository.RecolteRepository;
import com.example.citronix.repository.RecoltesDetailsRepository;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.ChampService;
import com.example.citronix.service.DTO.RecolteDTO;
import com.example.citronix.service.RecolteService;
import com.example.citronix.service.RecoltesDetailsService;
import com.example.citronix.web.VM.RecolteVM;
import com.example.citronix.web.errors.ChampUndefinedException;
import com.example.citronix.web.errors.DuplicateRecolteException;
import com.example.citronix.web.errors.RecolteUndefinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RecolteServiceImpl implements RecolteService {

    @Autowired
    private RecolteRepository recolteRepository;
    @Autowired
    private RecolteMapper recolteMapper;
    @Autowired
    private RecoltesDetailsRepository recoltesDetailsRepository;
    @Autowired
    @Lazy
    private RecoltesDetailsService recoltesDetailsService;
    @Autowired
    private ArbreService arbreService;
    @Autowired
    private ChampService champService;

    @Override
    public Recolte save(RecolteVM recolteVM) {
        // Validate and fetch the Champ
        Champ champ = champService.findById(recolteVM.getChamp())
                .orElseThrow(() -> new ChampUndefinedException("Il n'existe pas un champ avec cet ID"));

        // Map the RecolteVM to a Recolte entity
        Recolte recolte = recolteMapper.toEntityFromVM(recolteVM);

        // Check for existing recolte with the same `saison` and `date_de_recolte`
        checkDuplicateRecolte(recolteVM.getSaison(), recolte.getDate_de_recolte());

        // Save the Recolte
        Recolte savedRecolte = recolteRepository.save(recolte);

        // Filter Arbres by age and create RecoltesDetails
        List<RecoltesDetails> recoltesDetailsList = champ.getArbres().stream()
                .filter(arbre -> arbre.getAge() < 20) // Include only Arbres under 20 years old
                .map(arbre -> {
                    RecoltesDetails detail = new RecoltesDetails();
                    detail.setArbre(arbre);
                    detail.setQuantite(arbre.calculteAnnualProductivity());
                    detail.setRecolte(savedRecolte);
                    return detail;
                })
                .collect(Collectors.toList());

        // Save the RecoltesDetails
        recoltesDetailsService.saveAll(recoltesDetailsList);

        // Calculate total quantity from saved RecoltesDetails
        double totalQuantity = recoltesDetailsList.stream()
                .mapToDouble(RecoltesDetails::getQuantite)
                .sum();
        savedRecolte.setQuatiteTotale(totalQuantity);
        savedRecolte.setRecoltesDetails(recoltesDetailsList);

        // Update and return the Recolte
        return recolteRepository.save(savedRecolte);
    }


    private void checkDuplicateRecolte(Saison saison, LocalDateTime dateDeRecolte) {
        Optional<Recolte> existingRecolte = recolteRepository.findBySaisonAndDateDeRecolte(saison, dateDeRecolte);
        if (existingRecolte.isPresent()) {
            throw new DuplicateRecolteException("Une récolte avec cette saison et cette date existe déjà");
        }
    }


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

//        if (recolte.getRecoltesDetails() != null && !recolte.getRecoltesDetails().isEmpty()) {
//            recoltesDetailsRepository.deleteAll(recolte.getRecoltesDetails());
//        }

        recolteRepository.delete(recolte);
    }

    @Override
    public Page<Recolte> findAll(Pageable pageable) {
        return recolteRepository.findAll(pageable);
    }

}