package com.example.citronix.service.impl;

import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import com.example.citronix.repository.ChampRepository;
import com.example.citronix.service.FermeService;
import com.example.citronix.web.errors.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChampServiceImplTest {

    @InjectMocks
    private ChampServiceImpl champService;

    @Mock
    private FermeService fermeService;

    @Mock
    private ChampRepository champRepository;

    private Champ champ;
    private Ferme ferme;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Setup common test data
        ferme = new Ferme();
        ferme.setNom("Ferme1");
        ferme.setSuperficie(10000.0); // Setting some base superficie

        champ = new Champ();
        champ.setNom("champ1");
        champ.setSuperficie(2000.0);
        champ.setFerme(ferme);
    }

    @Test
    void shouldThrowFermeUndefinedException_whenFermeDoesNotExist() {
        // Setup mock behavior
        when(fermeService.findByNom(ferme.getNom())).thenReturn(Optional.empty());

        // Perform the assertion and check if exception is thrown
        assertThrows(FermeUndefinedException.class,
                () -> champService.save(champ),
                "Il n'existe pas une ferme avec ce nom");
    }

    @Test
    public void testSave_Success() {
        // Given
        Champ champ = new Champ();
        champ.setNom("Champ1");
        champ.setSuperficie(2000.0);
        Ferme ferme = new Ferme();
        ferme.setNom("Ferme1");
        champ.setFerme(ferme);

        when(fermeService.findByNom("Ferme1")).thenReturn(Optional.of(ferme));
        when(champService.countByFerme(ferme)).thenReturn(5);
        when(champService.save(any(Champ.class))).thenReturn(champ);

        // When
        Champ savedChamp = champService.save(champ);

        // Then
        assertNotNull(savedChamp);
        assertEquals("Champ1", savedChamp.getNom());
    }

    @Test
    void shouldReturnEmpty_whenChampWithSameNameDoesNotExist() {
        // Mock the ChampRepository to return Optional.empty() when no Champ is found
        when(champRepository.findByNom(champ.getNom())).thenReturn(Optional.empty());

        // Call the findByNom method
        Optional<Champ> result = champService.findByNom(champ.getNom());

        // Verify that the result is empty
        assertFalse(result.isPresent());
    }

    @Test
    void shouldThrowChampAlreadyExistsException_whenChampWithSameNameExists() {
        when(fermeService.findByNom(ferme.getNom())).thenReturn(Optional.of(ferme));
        when(champService.findByNom(champ.getNom())).thenReturn(Optional.of(champ));

        assertThrows(ChampAlreadyExistsException.class,
                () -> champService.save(champ),
                "Un champ avec ce nom existe déjà");
    }

    @Test
    void shouldThrowTooManyChampsException_whenFermeHasMaxChamps() {
        when(fermeService.findByNom(ferme.getNom())).thenReturn(Optional.of(ferme));
        when(champService.findByNom(champ.getNom())).thenReturn(Optional.empty());
        when(champService.countByFerme(ferme)).thenReturn(10);

        assertThrows(TooManyChampsException.class,
                () -> champService.save(champ),
                "Une ferme ne peut pas avoir plus de 10 champs");
    }

    @Test
    void shouldThrowChampMustUnderException_whenChampSuperficieExceeds50Percent() {
        ferme.setSuperficie(3000.0); // Setting ferme superficie
        champ.setSuperficie(2000.0);  // More than 50% of ferme superficie

        when(fermeService.findByNom(ferme.getNom())).thenReturn(Optional.of(ferme));
        when(champService.findByNom(champ.getNom())).thenReturn(Optional.empty());
        when(champService.countByFerme(ferme)).thenReturn(5);

        assertThrows(ChampMustUnderException.class,
                () -> champService.save(champ),
                "La superficie du champ ne doit pas prendre plus de 50% du ferme");
    }

    @Test
    void shouldThrowSuperficieException_whenChampSuperficieLessThan1000() {
        champ.setSuperficie(500.0);

        when(fermeService.findByNom(ferme.getNom())).thenReturn(Optional.of(ferme));
        when(champService.findByNom(champ.getNom())).thenReturn(Optional.empty());
        when(champService.countByFerme(ferme)).thenReturn(5);

        assertThrows(SuperficieException.class,
                () -> champService.save(champ),
                "La superficie du champ doit etre soit 1000 ou plus");
    }

//    @Test
//    void shouldThrowSuperficieException_whenTotalChampSuperficieExceedsFermeSuperficie() {
//        when(fermeService.findByNom(ferme.getNom())).thenReturn(Optional.of(ferme));
//        when(champService.findByNom(champ.getNom())).thenReturn(Optional.empty());
//        when(champService.countByFerme(ferme)).thenReturn(5);
//        when(champService.isSuperficieExceedingLimit(champ)).thenReturn(true);
//
//        assertThrows(SuperficieException.class,
//                () -> champService.save(champ),
//                "La somme des superficies des champs doit être inférieure à la superficie de la ferme");
//    }

//    @Test
//    void shouldSaveChamp_whenAllValidationsPass() {
//        // Given
//        when(fermeService.findByNom(ferme.getNom())).thenReturn(Optional.of(ferme));
//        when(champService.findByNom(champ.getNom())).thenReturn(Optional.empty());
//        when(champService.countByFerme(ferme)).thenReturn(5);
//        when(champService.isSuperficieExceedingLimit(champ)).thenReturn(false);
//        when(champRepository.save(champ)).thenReturn(champ);
//
//        // When
//        Champ savedChamp = champService.save(champ);
//
//        // Then
//        assertNotNull(savedChamp);
//        assertEquals(champ.getNom(), savedChamp.getNom());
//        verify(champRepository).save(champ);
//    }
//    @Test
//    public void test_findByNom_success(){
//        Champ champ = new Champ();
//        champ.setNom("champ");
//        champ.setSuperficie(2000);
//        Ferme ferme = new Ferme();
//        ferme.setNom("Ferme1");
//        champ.setFerme(ferme);
//
//        when(champRepository.findByNom("champ")).thenReturn(Optional.of(champ));
//
//        when(champService.findByNom("champ")).thenReturn(Optional.of(champ));
//
//        assertThrows(ChampAlreadyExistsException.class, () -> champService.save(champ));
//    }

}