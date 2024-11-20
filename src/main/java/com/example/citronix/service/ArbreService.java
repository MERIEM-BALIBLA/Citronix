package com.example.citronix.service;

import com.example.citronix.domain.Arbre;
import com.example.citronix.mapper.ArbreMapper;
import com.example.citronix.repository.ArbreRepository;
import com.example.citronix.service.DTO.ArbreDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface ArbreService {

    ArbreDTO save(ArbreDTO arbreDTO);

    Optional<Arbre> findById(UUID id);

    ArbreDTO update(UUID id, ArbreDTO arbreDTO);

    void delete(UUID id);
}

