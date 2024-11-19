//package com.example.citronix.repository;
//
//import com.example.citronix.domain.Arbre;
//import com.example.citronix.domain.enums.ArbreAge;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//public interface ArbreRepository extends JpaRepository<Arbre, UUID> {
//    Optional<Arbre> findById(UUID id);
//
//    List<Arbre> findByAge(ArbreAge age);
//
//    List<Arbre> findByDate_de_plantation(LocalDateTime dateTime);
//
//}
