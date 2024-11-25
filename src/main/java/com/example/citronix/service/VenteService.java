package com.example.citronix.service;

import com.example.citronix.domain.Vente;
import com.example.citronix.service.DTO.VenteDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface VenteService {
    VenteDTO save(VenteDTO venteDTO);

    Optional<Vente> findById(UUID id);

    void delete(UUID id);

    VenteDTO update(UUID id, VenteDTO venteDTO);

    Page<VenteDTO> findAll(Pageable pageable);
}
