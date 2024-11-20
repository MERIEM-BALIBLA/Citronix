package com.example.citronix.web.api;

import com.example.citronix.domain.Recolte;
import com.example.citronix.domain.RecoltesDetails;
import com.example.citronix.mapper.RecoltesDetailsMapper;
import com.example.citronix.service.DTO.RecoltesDetailsDTO;
import com.example.citronix.service.RecoltesDetailsService;
import com.example.citronix.web.VM.RecoltesDetailsVM;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/list")
    public Page<RecoltesDetailsVM> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<RecoltesDetails> recoltesDetailsPage = recoltesDetailsService.findAll(pageable);

        Page<RecoltesDetailsVM> recoltesDetailsVMPage = recoltesDetailsPage.map(recoltesDetailsMapper::toVM);
        return recoltesDetailsVMPage;
    }

}
