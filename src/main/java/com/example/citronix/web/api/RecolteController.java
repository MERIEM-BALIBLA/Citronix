package com.example.citronix.web.api;

import com.example.citronix.domain.Recolte;
import com.example.citronix.mapper.RecolteMapper;
import com.example.citronix.service.DTO.RecolteDTO;
import com.example.citronix.service.RecolteService;
import com.example.citronix.web.VM.RecolteVM;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/recolte")
public class RecolteController {

    private final RecolteService recolteService;
    private final RecolteMapper recolteMapper;

    public RecolteController(RecolteService recolteService, RecolteMapper recolteMapper) {
        this.recolteService = recolteService;
        this.recolteMapper = recolteMapper;
    }


    @PostMapping("/create")
    public ResponseEntity<RecolteVM> createRecolte(@Valid @RequestBody RecolteVM recolteVM) {
        Recolte createdRecolte = recolteService.save(recolteVM);
        return ResponseEntity.ok(recolteMapper.toVM(createdRecolte));
    }
//    public ResponseEntity<RecolteVM> save(@RequestBody @Valid RecolteVM recolteVM) {
//        RecolteDTO recolteDTO = recolteMapper.toDTO(recolteMapper.toEntityFromVM(recolteVM));
//        RecolteDTO savedRecolte = recolteService.save(recolteDTO);
//        return ResponseEntity.ok(recolteMapper.toVM(recolteMapper.toEntityFromDTO(savedRecolte)));
//    }

    @PutMapping("/{id}")
    public ResponseEntity<RecolteVM> update(@PathVariable UUID id, @RequestBody @Valid RecolteVM recolteVM) {
        RecolteDTO recolteDTO = recolteMapper.toDTO(recolteMapper.toEntityFromVM(recolteVM));
        RecolteDTO savedRecolte = recolteService.update(id, recolteDTO);
        return ResponseEntity.ok(recolteMapper.toVM(recolteMapper.toEntityFromDTO(savedRecolte)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        recolteService.delete(id);
        return ResponseEntity.ok("Le récolte a été bien supprimé");
    }

    @GetMapping
    public Page<Recolte> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return recolteService.findAll(pageable);
    }
}


