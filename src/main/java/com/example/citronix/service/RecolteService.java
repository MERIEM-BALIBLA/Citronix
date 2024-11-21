package com.example.citronix.service;

import com.example.citronix.domain.Recolte;
import com.example.citronix.service.DTO.RecolteDTO;
import com.example.citronix.web.VM.RecolteVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface RecolteService {
    Recolte save(RecolteVM recolteDTO);

    Optional<Recolte> findById(UUID id);

    RecolteDTO update(UUID id, RecolteDTO recolteDTO);

    void delete(UUID id);

    Page<Recolte> findAll(Pageable pageable);
}
