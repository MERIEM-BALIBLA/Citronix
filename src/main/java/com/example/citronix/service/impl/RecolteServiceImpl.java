package com.example.citronix.service.impl;

import com.example.citronix.domain.Recolte;
import com.example.citronix.mapper.RecolteMapper;
import com.example.citronix.repository.RecolteRepository;
import com.example.citronix.service.DTO.RecolteDTO;
import com.example.citronix.service.RecolteService;
import com.example.citronix.web.errors.DuplicateRecolteException;
import com.example.citronix.web.errors.InvalidRecolteException;
import com.example.citronix.web.errors.RecolteUndefinedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecolteServiceImpl implements RecolteService {

    private final RecolteRepository recolteRepository;
    private final RecolteMapper recolteMapper;

    public RecolteServiceImpl(RecolteRepository recolteRepository, RecolteMapper recolteMapper) {
        this.recolteRepository = recolteRepository;
        this.recolteMapper = recolteMapper;
    }

    @Override
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
        recolteRepository.delete(optionalRecolte.get());
    }

    @Override
    public Page<Recolte> findAll(Pageable pageable) {
        return recolteRepository.findAll(pageable);
    }

}