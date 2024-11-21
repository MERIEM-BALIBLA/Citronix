package com.example.citronix.service;

import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import com.example.citronix.service.DTO.ChampDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface ChampService {

    Optional<Champ> findByNom(String nom);

    Optional<Champ> findByFerme(Ferme ferme);

    ChampDTO save(ChampDTO champDTO);

    Optional<Champ> findById(UUID id);

    ChampDTO update(UUID id, ChampDTO champDTO);

    void delete(UUID id);

    Page<Champ> findAll(Pageable pageable);

    void deleteAll(List<Champ> champList);
}
