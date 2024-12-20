package com.example.citronix;

import com.example.citronix.domain.Arbre;
import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import com.example.citronix.repository.ArbreRepository;
import com.example.citronix.repository.ChampRepository;
import com.example.citronix.repository.FermeRepository;
import com.example.citronix.service.FermeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CitronixApplication implements CommandLineRunner {

    @Autowired
    private ChampRepository champRepository;

    @Autowired
    private FermeService fermeService;

    @Autowired
    private FermeRepository fermeRepository;

    @Autowired
    private ArbreRepository arbreRepository;

    public static void main(String[] args) {
        SpringApplication.run(CitronixApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<Champ> champList = champRepository.findAll();
        List<Ferme> fermesList = fermeRepository.findAll();
        List<Arbre> arbreList = arbreRepository.findAll();

    }
}
