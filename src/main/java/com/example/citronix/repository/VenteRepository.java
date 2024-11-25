package com.example.citronix.repository;

import com.example.citronix.domain.Recolte;
import com.example.citronix.domain.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VenteRepository extends JpaRepository<Vente, UUID> {
    Optional<Vente> findByRecolte(Recolte recolte);
}
