package com.example.citronix.validation;

import com.example.citronix.domain.Recolte;
import com.example.citronix.repository.RecolteRepository;
import com.example.citronix.web.errors.DuplicateRecolteException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecolteValidation {

    private final RecolteRepository recolteRepository;

    public RecolteValidation(RecolteRepository recolteRepository) {
        this.recolteRepository = recolteRepository;
    }

    public void seuleRecolteParSaison(Recolte recolte){
        Optional<Recolte> exists = recolteRepository.findBySaisonAndDateDeRecolte(recolte.getSaison(), recolte.getDate_de_recolte());
        if (exists.isPresent()) {
            throw new DuplicateRecolteException("Une récolte avec cette saison et cette date existe déjà");
        }
    }
}
