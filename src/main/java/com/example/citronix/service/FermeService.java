package com.example.citronix.service;

import com.example.citronix.domain.Ferme;
import com.example.citronix.service.DTO.FermeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface FermeService {

    Optional<Ferme> findByNom(String nom);

    Ferme save(Ferme ferme);

    List<Ferme> findAll();

    FermeDTO update(UUID id, FermeDTO fermeDTO);

    void delete(UUID id);

//    List<Ferme> getFieldsGraterThan();

//    boolean verifierSuperficieDeFerme(Ferme ferme);

    Ferme getFermeDatails(String nom);

    Ferme search(String name, String location, Double area);
}
