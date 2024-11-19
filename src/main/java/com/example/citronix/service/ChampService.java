package com.example.citronix.service;

import com.example.citronix.service.DTO.ChampDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ChampService {

    ChampDTO save(ChampDTO champDTO);

    ChampDTO update(ChampDTO champDTO);

    void delete(UUID id);
}
