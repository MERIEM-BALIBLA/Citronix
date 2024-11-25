/*
package com.example.citronix;

import com.example.citronix.domain.Champ;
import com.example.citronix.service.impl.ChampServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class CitronixApplication {
    @Autowired
    private ChampServiceImpl champService;

    public static void main(String[] args) {
        SpringApplication.run(CitronixApplication.class, args);
    }

    public void run(String... args) throws Exception {
        // This code runs after Spring Boot application starts
        Optional<Champ> champOptional = champService.findByNom("chyppo");
        champOptional.ifPresent(System.out::println);
    }
}
*/
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
//        fermesList.forEach(ferrme->System.out.println(ferrme.getNom()));
        /*fermesList.forEach(ferme -> {
            double superficieTotal = ferme.getChamps().stream()
                    .mapToDouble(Champ::getSuperficie)
                    .sum(); // Somme des superficies des champs de la ferme
            System.out.println("Ferme: " + ferme.getNom() + ", Superficie totale: " + superficieTotal);
        });*/
//        fermesList.stream().
//                filter(ferme -> {
//                    return ferme.getChamps().stream().mapToDouble(Champ::getSuperficie).sum()>100;
//                })
//                .forEach(ferme -> {
//                    double totalsuper = ferme.getChamps().stream().mapToDouble(Champ::getSuperficie).sum();
//                    System.out.println(ferme.getNom() + ":" + totalsuper);
//                });


        List<Ferme> fermes = fermeRepository.findAll();

//        fermes.forEach(ferme -> {
//            double productivite = ferme.getChamps().stream()
//                    .flatMap(champ -> champ.getArbres().stream())
//                    .mapToDouble(Arbre::calculteAnnualProductivity).sum();
//            System.out.println(ferme.getNom() + " " + productivite);
//        });


        champList.stream()
                .forEach(champ -> {
                    // Filtrage des arbres de plus de 10 ans
                    List<Arbre> arbresFiltrés = champ.getArbres().stream()
                            .filter(arbre -> arbre.getAge() > 10)
                            .toList();

                    // Si la liste des arbres filtrés n'est pas vide, afficher le nom du champ et l'âge des arbres
                    if (!arbresFiltrés.isEmpty()) {
                        System.out.print(champ.getNom() + "; ");
                        arbresFiltrés.forEach(arbre -> System.out.print(arbre.getAge() + " "));
                        System.out.println(); // Pour sauter à la ligne après chaque champ
                    }
                });


//        fermesList.forEach(ferme -> System.out.println(ferme.getNom() + ));

        /*for (int i = 0; i < champList.size() - 1; i++) {
            for (int j = i; j < champList.size(); j++) {
                if (champList.get(i).getSuperficie() < champList.get(j).getSuperficie()) {
                    Champ temp = champList.get(i);
                    champList.set(i, champList.get(j));
                    champList.set(j, temp);
                }
            }
        }

        for(Champ champ:champList){
            System.out.println(champ.getNom() + ":" + champ.getSuperficie());
        }*/
    }
}
