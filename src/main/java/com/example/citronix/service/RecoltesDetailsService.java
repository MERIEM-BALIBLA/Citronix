package com.example.citronix.service;

import com.example.citronix.domain.RecoltesDetails;
import com.example.citronix.service.DTO.RecoltesDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface RecoltesDetailsService {
    RecoltesDetailsDTO save(RecoltesDetailsDTO recoltesDetailsDTO);

    List<RecoltesDetails> saveAll(List<RecoltesDetails> recoltesDetails);

    Optional<RecoltesDetails> findById(UUID id);

    RecoltesDetailsDTO update(UUID id, RecoltesDetailsDTO recoltesDetailsDTO);

    void delete(UUID id);

    Page<RecoltesDetails> findAll(Pageable pageable);
}
