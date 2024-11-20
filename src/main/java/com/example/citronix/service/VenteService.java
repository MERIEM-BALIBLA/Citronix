package com.example.citronix.service;

import com.example.citronix.service.DTO.VenteDTO;
import org.springframework.stereotype.Service;

@Service
public interface VenteService {
    VenteDTO save(VenteDTO venteDTO);
}
