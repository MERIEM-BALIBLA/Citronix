package com.example.citronix.service.impl;

import com.example.citronix.domain.Recolte;
import com.example.citronix.domain.Vente;
import com.example.citronix.mapper.VenteMapper;
import com.example.citronix.repository.VenteRepository;
import com.example.citronix.service.DTO.VenteDTO;
import com.example.citronix.service.RecolteService;
import com.example.citronix.service.VenteService;
import com.example.citronix.web.errors.RecolteUndefinedException;
import com.example.citronix.web.errors.VenteAlreadyExistException;
import com.example.citronix.web.errors.VenteUndefinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class VenteServiceImpl implements VenteService {

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private VenteMapper venteMapper;

    @Autowired
    @Lazy
    private RecolteService recolteService;

    @Override
    public Optional<Vente> findById(UUID id) {
        return venteRepository.findById(id);
    }

    private void validation(Vente vente) {
        Recolte recolte = vente.getRecolte();
        Optional<Recolte> optionalRecolte = recolteService.findById(recolte.getId());
        if (optionalRecolte.isEmpty()) {
            throw new RecolteUndefinedException("Il n'existe pas un recolte avec ce ID");
        }

        Optional<Vente> venteOptional = venteRepository.findByRecolte(recolte);
        if (venteOptional.isPresent()) {
            throw new VenteAlreadyExistException("Il existe déjà une vente pour cette recolte");
        }
    }

    @Override
    public VenteDTO save(VenteDTO venteDTO) {
        // Verify recolte exists
        Recolte optionalRecolte = recolteService.findById(venteDTO.getRecolteId())
                .orElseThrow(() -> new RuntimeException("Recolte not found"));

        // Validate critical fields
        if (venteDTO.getPrix_unitaire() == null) {
            throw new IllegalStateException("Prix unitaire cannot be null");
        }

        if (optionalRecolte.getQuatiteTotale() == null) {
            throw new IllegalStateException("Quantité totale cannot be null");
        }

        Vente vente = venteMapper.fromDTOtoEntity(venteDTO);

        // Set the fetched recolte
        vente.setRecolte(optionalRecolte);

        // Save the vente
        validation(vente);
        Vente savedVente = venteRepository.save(vente);

        // Convert to VM and set prevenu
        VenteDTO venteDTO1 = venteMapper.toDTO(savedVente);
        venteDTO1.setPrevenu(savedVente.calculRevenu());

        return venteDTO1;
    }

    @Override
    public void delete(UUID id) {
        Optional<Vente> venteOptional = findById(id);
        Vente vente = venteOptional.get();
//        Recolte recolte = vente.getRecolte();
//        if (recolte != null) {
//            recolteService.delete(recolte.getId());
//        }
        venteRepository.delete(vente);
    }

    @Override
    public VenteDTO update(UUID id, VenteDTO venteDTO) {
        Optional<Vente> venteOptional = findById(id);
        if (venteOptional.isEmpty()) {
            throw new VenteUndefinedException("Il n'existe pas un vente avec ce ID");
        }
        Vente vente = venteMapper.fromDTOtoEntity(venteDTO);
        Vente existingVente = venteOptional.get();

        existingVente.setDate_de_vente(vente.getDate_de_vente());
        existingVente.setRecolte(vente.getRecolte());
        existingVente.setClientName(vente.getClientName());
        existingVente.setPrix_unitaire(vente.getPrix_unitaire());

//        validation(vente);

        venteRepository.save(existingVente);
        return venteMapper.toDTO(existingVente);
    }

    @Override
    public Page<VenteDTO> findAll(Pageable pageable) {

        Page<Vente> venteRepositoryAll = venteRepository.findAll(pageable);

        Page<VenteDTO> venteDTOS = venteRepositoryAll.map(venteMapper::toDTO);

        venteDTOS.forEach(venteDTO -> {
            venteDTO.setPrevenu(venteDTO.getPrix_unitaire());
        });

        return venteDTOS;
    }

//    public Vente saveVente(Vente vente) {
////        Vente a un attribut quantite
//        Recolte recolte = vente.getRecolte();
//
//        List<Vente> ventes = recolte.getVentes();
//        double totalQuantite = ventes.stream().mapToDouble(vente1 -> vente1.getRecolte().getQuatiteTotale()).sum();
//
//        if (totalQuantite < recolte.getQuatiteTotale()) {
//            venteRepository.save(vente);
//        double new_quanityTotal = vente.getRecolte().getQuatiteTotale() _ vente.getQuantite;
//          recolte.setQuatiteTotale(new_quanityTotal);
//        recolteService.update(recolte);
//        }
//
//    }

}
