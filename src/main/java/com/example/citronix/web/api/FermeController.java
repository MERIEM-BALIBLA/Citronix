package com.example.citronix.web.api;

import com.example.citronix.domain.Ferme;
import com.example.citronix.mapper.FermeMapper;
import com.example.citronix.service.DTO.FermeDTO;
import com.example.citronix.service.FermeService;
import com.example.citronix.web.VM.FermeVM;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ferme")
public class FermeController {

    @Autowired
    private FermeService fermeService;
    @Autowired
    private FermeMapper fermeMapper;


    @PostMapping("/create")
    public ResponseEntity<FermeVM> save(@RequestBody @Valid FermeVM fermeVM) {
        FermeDTO fermeDTO = fermeMapper.toDTO(fermeMapper.toEntity(fermeVM));
        FermeDTO ferme = fermeService.save(fermeDTO);
        return ResponseEntity.ok(fermeMapper.toVM(fermeMapper.toEntity(ferme)));
    }

    @GetMapping("/list")
    public ResponseEntity<List<FermeVM>> fermesLst() {
        List<Ferme> fermes = fermeService.findAll();
        List<FermeVM> fermeVMS = fermeMapper.toVMs(fermes);
        return ResponseEntity.ok(fermeVMS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FermeVM> update(@PathVariable UUID id, @Valid @RequestBody FermeVM fermeVM) {
        FermeDTO fermeDTO = fermeMapper.toDTO(fermeMapper.toEntity(fermeVM));
        FermeDTO updatedFerme = fermeService.update(id, fermeDTO); // Pass UUID as ID
        return ResponseEntity.ok(fermeMapper.toVM(fermeMapper.toEntity(updatedFerme)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        fermeService.delete(id);
        return ResponseEntity.ok("Ferme a été bien supprimer");
    }

    @GetMapping("/{nom}")
    public ResponseEntity<FermeVM> getFermeDatails(@PathVariable String nom) {
        Ferme ferme = fermeService.getFermeDatails(nom);
        FermeVM fermeVM = fermeMapper.toVM(ferme);
        return ResponseEntity.ok(fermeVM);
    }

}
