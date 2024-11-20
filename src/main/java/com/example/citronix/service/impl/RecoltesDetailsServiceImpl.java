package com.example.citronix.service.impl;

import com.example.citronix.domain.Arbre;
import com.example.citronix.domain.Recolte;
import com.example.citronix.domain.RecoltesDetails;
import com.example.citronix.mapper.RecoltesDetailsMapper;
import com.example.citronix.repository.RecoltesDetailsRepository;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.DTO.RecoltesDetailsDTO;
import com.example.citronix.service.RecolteService;
import com.example.citronix.service.RecoltesDetailsService;
import com.example.citronix.web.errors.ArbreUndefinedException;
import com.example.citronix.web.errors.RecolteUndefinedException;
import com.example.citronix.web.errors.RecoltesDetailsUndefinedException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecoltesDetailsServiceImpl implements RecoltesDetailsService {

    private final RecoltesDetailsRepository recoltesDetailsRepository;
    private final RecoltesDetailsMapper recoltesDetailsMapper;
    private final ArbreService arbreService;
    private final RecolteService recolteService;

    public RecoltesDetailsServiceImpl(RecoltesDetailsRepository recoltesDetailsRepository, RecoltesDetailsMapper recoltesDetailsMapper, ArbreService arbreService, RecolteService recolteService) {
        this.recoltesDetailsRepository = recoltesDetailsRepository;
        this.recoltesDetailsMapper = recoltesDetailsMapper;
        this.arbreService = arbreService;
        this.recolteService = recolteService;
    }

    private void validateArbreAndRecolte(RecoltesDetails recoltesDetails) {
        Optional<Arbre> optionalArbre = arbreService.findById(recoltesDetails.getArbre().getId());
        if (optionalArbre.isEmpty()) {
            throw new ArbreUndefinedException("Il n'éxiste pas une arbre avec ce ID");
        }

        Optional<Recolte> optionalRecolte = recolteService.findById(recoltesDetails.getRecolte().getId());
        if (optionalRecolte.isEmpty()) {
            throw new RecolteUndefinedException("Il n'existe pas un recolte avec ce ID");
        }
    }

    @Override
    public RecoltesDetailsDTO save(RecoltesDetailsDTO recoltesDetailsDTO) {
        RecoltesDetails recoltesDetails = recoltesDetailsMapper.fromDTOtoEntity(recoltesDetailsDTO);

        // Validate Arbre and Recolte existence
        validateArbreAndRecolte(recoltesDetails);

        RecoltesDetails savedRecoltDetails = recoltesDetailsRepository.save(recoltesDetails);
        return recoltesDetailsMapper.toDTO(savedRecoltDetails);
    }

    @Override
    public Optional<RecoltesDetails> findById(UUID id) {
        return recoltesDetailsRepository.findById(id);
    }

    @Override
    public RecoltesDetailsDTO update(UUID id, RecoltesDetailsDTO recoltesDetailsDTO) {

        Optional<RecoltesDetails> optionalRecoltesDetails = findById(id);
        if (optionalRecoltesDetails.isEmpty()) {
            throw new RecoltesDetailsUndefinedException("Il n'existe pas un recolte details avec ce ID");
        }

        RecoltesDetails recoltesDetails = recoltesDetailsMapper.fromDTOtoEntity(recoltesDetailsDTO);
        RecoltesDetails existigRecoltesDetails = optionalRecoltesDetails.get();

        // Validate Arbre and Recolte existence
        validateArbreAndRecolte(recoltesDetails);

        existigRecoltesDetails.setRecolte(recoltesDetails.getRecolte());
        existigRecoltesDetails.setArbre(recoltesDetails.getArbre());
        existigRecoltesDetails.setQuantite(recoltesDetails.getQuantite());

        RecoltesDetails updatedRecoltesDetails = recoltesDetailsRepository.save(existigRecoltesDetails);
        return recoltesDetailsMapper.toDTO(updatedRecoltesDetails);
    }

    @Override
    public void delete(UUID id) {
        Optional<RecoltesDetails> recoltesDetailsOptional = findById(id);
        if (recoltesDetailsOptional.isEmpty()) {
            throw new RecoltesDetailsUndefinedException("Il n'existe pas un recolte details avec ce ID");
        }
        recoltesDetailsRepository.delete(recoltesDetailsOptional.get());
    }

}
