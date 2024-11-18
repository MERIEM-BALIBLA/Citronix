//package com.example.citronix.domain;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//public class Vente {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long id;
//
//    private LocalDateTime date;
//    private Double prix_unitaire;
//    @ManyToOne
//    private Recolte recolte;
//    private String clientName;
//    private Double revenu;
//
//}
