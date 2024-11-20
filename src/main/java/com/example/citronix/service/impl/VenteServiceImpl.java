package com.example.citronix.service.impl;

import com.example.citronix.domain.Vente;
import com.example.citronix.mapper.VenteMapper;
import com.example.citronix.repository.VenteRepository;
import com.example.citronix.service.DTO.VenteDTO;
import com.example.citronix.service.VenteService;
import org.springframework.stereotype.Component;

@Component
public class VenteServiceImpl implements VenteService {

    private final VenteRepository venteRepository;
    private final VenteMapper venteMapper;

    public VenteServiceImpl(VenteRepository venteRepository, VenteMapper venteMapper) {
        this.venteRepository = venteRepository;
        this.venteMapper = venteMapper;
    }

    @Override
    public VenteDTO save(VenteDTO venteDTO) {
        Vente vente = venteMapper.fromDTOtoEntity(venteDTO);
        Vente savedVente = venteRepository.save(vente);
        return venteMapper.toDTO(savedVente);
    }

}
