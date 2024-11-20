package com.example.citronix.web.api;

import com.example.citronix.domain.Arbre;
import com.example.citronix.mapper.ArbreMapper;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.DTO.ArbreDTO;
import com.example.citronix.web.VM.ArbreVM;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/arbre")
public class ArbreController {

    private final ArbreService arbreService;
    private final ArbreMapper arbreMapper;

    public ArbreController(ArbreService arbreService, ArbreMapper arbreMapper) {
        this.arbreService = arbreService;
        this.arbreMapper = arbreMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ArbreVM> save(@RequestBody @Valid ArbreVM arbreVM) {
        ArbreDTO arbreDTO = arbreMapper.toDTO(arbreMapper.toEntityFromVM(arbreVM));
        ArbreDTO savedArbre = arbreService.save(arbreDTO);
        return ResponseEntity.ok(arbreMapper.toVM(arbreMapper.toEntityFromDTO(savedArbre)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArbreVM> update(@PathVariable UUID id, @RequestBody @Valid ArbreVM arbreVM) {
        ArbreDTO arbreDTO = arbreMapper.toDTO(arbreMapper.toEntityFromVM(arbreVM));
        ArbreDTO savedArbre = arbreService.update(id, arbreDTO);
        return ResponseEntity.ok(arbreMapper.toVM(arbreMapper.toEntityFromDTO(savedArbre)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        arbreService.delete(id);
        return ResponseEntity.ok("Arbre a été bien supprimer");
    }

    @GetMapping("/list")
    public Page<ArbreVM> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Arbre> recoltesDetailsPage = arbreService.findAll(pageable);
        return recoltesDetailsPage.map(arbreMapper::toVM);
    }
}
