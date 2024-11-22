package com.example.citronix.web.api;

import com.example.citronix.mapper.VenteMapper;
import com.example.citronix.service.DTO.VenteDTO;
import com.example.citronix.service.VenteService;
import com.example.citronix.web.VM.VenteVM;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vente")
public class VenteController {

    private final VenteService venteService;
    private final VenteMapper venteMapper;

    public VenteController(VenteService venteService, VenteMapper venteMapper) {
        this.venteService = venteService;
        this.venteMapper = venteMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<VenteVM> save(@RequestBody @Valid VenteVM venteVM) {
        VenteDTO venteDTO = venteMapper.toDTO(venteMapper.fromVMtoEntity(venteVM));
        VenteDTO savedVente = venteService.save(venteDTO);
        return ResponseEntity.ok(venteMapper.toVM(venteMapper.fromDTOtoEntity(savedVente)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenteVM> update(@PathVariable UUID id, @RequestBody @Valid VenteVM venteVM) {
        VenteDTO venteDTO = venteMapper.toDTO(venteMapper.fromVMtoEntity(venteVM));
        VenteDTO savedVente = venteService.update(id, venteDTO);
        return ResponseEntity.ok(venteMapper.toVM(venteMapper.fromDTOtoEntity(savedVente)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        venteService.delete(id);
        return ResponseEntity.ok("Le vente a ete bien suprimé");
    }
}


