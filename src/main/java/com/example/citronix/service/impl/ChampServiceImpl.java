package com.example.citronix.service.impl;

import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import com.example.citronix.mapper.ChampMapper;
import com.example.citronix.repository.ChampRepository;
import com.example.citronix.service.ChampService;
import com.example.citronix.service.DTO.ChampDTO;
import com.example.citronix.service.FermeService;
import com.example.citronix.web.errors.ChampAlreadyExistsException;
import com.example.citronix.web.errors.FermeUndefinedException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ChampServiceImpl implements ChampService {

    private ChampRepository champRepository;
    private ChampMapper champMapper;
    private FermeService fermeService;

    public ChampServiceImpl(ChampRepository champRepository, ChampMapper champMapper, FermeService fermeService) {
        this.champRepository = champRepository;
        this.champMapper = champMapper;
        this.fermeService = fermeService;
    }

    @Override
    public ChampDTO save(ChampDTO champDTO) {
        String ferme_name = champDTO.getFerme_nom();
        Optional<Ferme> fermeOptional = fermeService.findByNom(ferme_name);
        if (fermeOptional.isEmpty()) {
            throw new FermeUndefinedException("il n'exite pas une ferme avec ce nom");
        }

        Ferme ferme = fermeOptional.get();

        // Check if a Champ already exists with the same Ferme and superficie
        boolean exists = champRepository.existsByFermeAndSuperficie(ferme, champDTO.getSuperficie());
        if (exists) {
            throw new ChampAlreadyExistsException("Un champ avec cette ferme et superficie existe déjà");
        }

        Champ champ = champMapper.toEntity(champDTO);

        champ.setFerme(fermeOptional.get());
        Champ champSaved = champRepository.save(champ);
        return champMapper.toDTO(champSaved);
    }

    @Override
    public ChampDTO update(ChampDTO champDTO){
        return null;
    }

    @Override
    public void delete(UUID id){
    }

}
