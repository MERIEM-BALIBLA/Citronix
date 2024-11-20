package com.example.citronix.repository;

import com.example.citronix.domain.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VenteRepository extends JpaRepository<Vente, UUID> {
}
