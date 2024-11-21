package com.example.citronix.service.impl;

import com.example.citronix.domain.Recolte;
import com.example.citronix.domain.Vente;
import com.example.citronix.mapper.VenteMapper;
import com.example.citronix.repository.VenteRepository;
import com.example.citronix.service.DTO.VenteDTO;
import com.example.citronix.service.RecolteService;
import com.example.citronix.service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class VenteServiceImpl implements VenteService {

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private VenteMapper venteMapper;

    @Autowired
    @Lazy
    private RecolteService recolteService;

    @Override
    public VenteDTO save(VenteDTO venteDTO) {
        Vente vente = venteMapper.fromDTOtoEntity(venteDTO);
        Vente savedVente = venteRepository.save(vente);
        return venteMapper.toDTO(savedVente);
    }

    @Override
    public Optional<Vente> findById(UUID id) {
        return venteRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        Optional<Vente> venteOptional = findById(id);
        Vente vente = venteOptional.get();
        Recolte recolte = vente.getRecolte();
        if (recolte != null) {
            recolteService.delete(recolte.getId());
        }
        venteRepository.delete(vente);
    }
}
