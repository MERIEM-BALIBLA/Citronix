package com.example.citronix.web.api;

import com.example.citronix.mapper.ChampMapper;
import com.example.citronix.service.ChampService;
import com.example.citronix.service.DTO.ChampDTO;
import com.example.citronix.web.VM.ChampVM;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/champ")
public class ChampController {

    private ChampService champService;
    private ChampMapper champMapper;

    public ChampController(ChampService champService, ChampMapper champMapper) {
        this.champService = champService;
        this.champMapper = champMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ChampDTO> save(@RequestBody @Valid ChampVM champVM) {
        ChampDTO champDTO = champMapper.toDTO(champMapper.toEntity(champVM));
        return ResponseEntity.ok(champService.save(champDTO));
    }

}
