package com.example.citronix.web.api;

import com.example.citronix.mapper.ArbreMapper;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.DTO.ArbreDTO;
import com.example.citronix.web.VM.ArbreVM;
import jakarta.validation.Valid;
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

}
