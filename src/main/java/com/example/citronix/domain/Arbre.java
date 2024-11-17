//package com.example.citronix.domain;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//public class Arbre {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long id;
//    private LocalDateTime date_de_plantation;
//    private int age;
//    @ManyToOne
//    private Champ champ;
//    private Double productivité;
//
//    @OneToMany
//    List<RecoltesDetails> recoltesDetails;
//}
