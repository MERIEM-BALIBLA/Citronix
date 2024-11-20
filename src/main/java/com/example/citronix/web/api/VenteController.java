package com.example.citronix.web.api;

import com.example.citronix.mapper.VenteMapper;
import com.example.citronix.service.DTO.VenteDTO;
import com.example.citronix.service.VenteService;
import com.example.citronix.web.VM.VenteVM;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
