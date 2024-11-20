package com.example.citronix.web.api;

import com.example.citronix.mapper.ChampMapper;
import com.example.citronix.service.ChampService;
import com.example.citronix.service.DTO.ChampDTO;
import com.example.citronix.web.VM.ChampVM;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/champ")
public class ChampController {

    private final ChampService champService;
    private final ChampMapper champMapper;

    public ChampController(ChampService champService, ChampMapper champMapper) {
        this.champService = champService;
        this.champMapper = champMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ChampVM> save(@RequestBody @Valid ChampVM champVM) {
        ChampDTO champDTO = champMapper.toDTO(champMapper.toEntity(champVM));
        ChampDTO savedChamp = champService.save(champDTO);
        return ResponseEntity.ok(champMapper.toVM(champMapper.toEntity(savedChamp)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChampVM> update(@PathVariable UUID id, @Valid @RequestBody ChampVM champVM) {
        ChampDTO champDTO = champMapper.toDTO(champMapper.toEntity(champVM));
        ChampDTO savedChamp = champService.update(id, champDTO);
        return ResponseEntity.ok(champMapper.toVM(champMapper.toEntity(savedChamp)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        champService.delete(id);
        return ResponseEntity.ok("Champ a été bien supprimer");
    }

}
