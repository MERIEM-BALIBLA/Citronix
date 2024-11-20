package com.example.citronix.web.api;

import com.example.citronix.mapper.RecoltesDetailsMapper;
import com.example.citronix.service.DTO.RecoltesDetailsDTO;
import com.example.citronix.service.RecoltesDetailsService;
import com.example.citronix.web.VM.RecoltesDetailsVM;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/recolte_details")
public class RecoltesDetailsController {

    private final RecoltesDetailsService recoltesDetailsService;
    private final RecoltesDetailsMapper recoltesDetailsMapper;

    public RecoltesDetailsController(RecoltesDetailsService recoltesDetailsService, RecoltesDetailsMapper recoltesDetailsMapper) {
        this.recoltesDetailsService = recoltesDetailsService;
        this.recoltesDetailsMapper = recoltesDetailsMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<RecoltesDetailsVM> save(@RequestBody @Valid RecoltesDetailsVM recoltesDetailsVM) {
        RecoltesDetailsDTO recoltesDetailsDTO = recoltesDetailsMapper.toDTO(recoltesDetailsMapper.fromVMtoEntity(recoltesDetailsVM));
        RecoltesDetailsDTO savedRecoltesDetailsDTO = recoltesDetailsService.save(recoltesDetailsDTO);
        return ResponseEntity.ok(recoltesDetailsMapper.toVM(recoltesDetailsMapper.fromDTOtoEntity(savedRecoltesDetailsDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecoltesDetailsVM> update(@PathVariable UUID id, @RequestBody @Valid RecoltesDetailsVM recoltesDetailsVM) {
        RecoltesDetailsDTO recoltesDetailsDTO = recoltesDetailsMapper.toDTO(recoltesDetailsMapper.fromVMtoEntity(recoltesDetailsVM));
        RecoltesDetailsDTO savedRecoltesDetailsDTO = recoltesDetailsService.update(id, recoltesDetailsDTO);
        return ResponseEntity.ok(recoltesDetailsMapper.toVM(recoltesDetailsMapper.fromDTOtoEntity(savedRecoltesDetailsDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        recoltesDetailsService.delete(id);
        return ResponseEntity.ok("Le detail du recolte a été bien supprimé");
    }
}
