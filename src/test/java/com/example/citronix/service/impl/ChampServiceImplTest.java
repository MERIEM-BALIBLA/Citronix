package com.example.citronix.service.impl;

import com.example.citronix.domain.Arbre;
import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import com.example.citronix.mapper.ChampMapper;
import com.example.citronix.repository.ChampRepository;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.DTO.ChampDTO;
import com.example.citronix.service.FermeService;
import com.example.citronix.web.errors.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChampServiceImplTest {

    @InjectMocks
    private ChampServiceImpl champService;

    @Mock
    private FermeService fermeService;

    @Mock
    private ChampRepository champRepository;

    @Mock
    private ArbreService arbreService;

    @Mock
    private ChampMapper champMapper;

    private Champ champ;
    private Ferme ferme;
    private Arbre arbre1;
    private Arbre arbre2;
    private ChampDTO champDTO;

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

        arbre1 = new Arbre(); // Initialize arbre1
        arbre1.setId(UUID.randomUUID());
        arbre1.setChamp(champ);

        arbre2 = new Arbre(); // Initialize arbre2
        arbre2.setId(UUID.randomUUID());
        arbre2.setChamp(champ);

        champDTO = new ChampDTO();
        champDTO.setNom("Nouveau Champ");
        champDTO.setSuperficie(150.0);
        champDTO.setFerme("Ferme Test");
    }

    //    ---------------------------------------------------------------------------------------
    @Test
    public void save_champ() {
        // Mock
        when(fermeService.findByNom(ferme.getNom())).thenReturn(Optional.of(ferme));
        when(champRepository.save(champ)).thenReturn(champ);

        // Action
        Champ savedChamp = champService.save(champ);

        // Assert(verification)
        assertNotNull(savedChamp);
        verify(fermeService).findByNom(ferme.getNom());
        verify(champRepository).save(champ);
    }

    @Test
    void findByNom_shouldReturnExceptionFerme() {
        when(fermeService.findByNom(ferme.getNom())).thenReturn(Optional.empty());
        assertThrows(FermeUndefinedException.class,
                () -> champService.save(champ),
                "Il n'existe pas une ferme avec ce nom");
    }

    @Test
    void findByNom_shouldReturnChamp() {
        when(champRepository.findByNom(champ.getNom())).thenReturn(Optional.of(champ));

        Optional<Champ> result = champService.findByNom(champ.getNom());

        assertNotNull(result);
        assertTrue(result.isPresent());

        verify(champRepository).findByNom(champ.getNom());
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
    void findById_shouldReturnChamp() {
        when(champRepository.findById(champ.getId())).thenReturn(Optional.of(champ));

        Optional<Champ> champF = champService.findById(champ.getId());

        assertNotNull(champF);
        assertTrue(champF.isPresent());

        verify(champRepository).findById(champ.getId());
    }


    @Test
    void validateChampCount_WhenTooManyChamps() {
        when(champRepository.countByFerme(ferme)).thenReturn(10);
        TooManyChampsException exception = assertThrows(
                TooManyChampsException.class,
                () -> champService.validateChampCount(ferme)
        );
        assertEquals("Une ferme ne peut pas avoir plus de 10 champs", exception.getMessage());
        verify(champRepository).countByFerme(ferme);
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
    public void testSave_ShouldReturnSavedChamp() {
        // Given
        Ferme ferme = new Ferme();
        ferme.setNom("Ferme1");
        ferme.setSuperficie(10000.0);
        ferme.setChamps(new ArrayList<>());

        Champ champ = new Champ();
        champ.setNom("Champ1");
        champ.setSuperficie(2000.0);
        champ.setFerme(ferme);

        // Mock
        when(fermeService.findByNom("Ferme1")).thenReturn(Optional.of(ferme));
        when(champRepository.save(champ)).thenReturn(champ);
        when(champService.countByFerme(ferme)).thenReturn(5);
        when(champService.findByNom(champ.getNom())).thenReturn(Optional.empty());

        // When
        Champ savedChamp = champService.save(champ);

        // Then
        assertNotNull(savedChamp, "The saved Champ should not be null");
        assertEquals("Champ1", savedChamp.getNom(), "The Champ name should match");
        assertEquals(2000.0, savedChamp.getSuperficie(), "The Champ superficie should match");
        assertEquals("Ferme1", savedChamp.getFerme().getNom(), "The associated Ferme name should match");
    }


    @Test
    void shouldThrowChampMustUnderException_whenChampSuperficieExceeds50Percent() {
        ferme.setSuperficie(3000.0);
        champ.setSuperficie(2000.0);

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

    @Test
    void isSuperficieExceedingLimit_shouldReturnTrue_whenTotalSuperficieExceedsLimit() {
        // Given
        Ferme ferme = new Ferme();
        ferme.setSuperficie(5000.0);

        Champ existingChamp1 = new Champ();
        existingChamp1.setSuperficie(2000.0);
        existingChamp1.setFerme(ferme);

        Champ existingChamp2 = new Champ();
        existingChamp2.setSuperficie(2000.0);
        existingChamp2.setFerme(ferme);

        List<Champ> existingChamps = Arrays.asList(existingChamp1, existingChamp2);
        ferme.setChamps(existingChamps);

        Champ newChamp = new Champ();
        newChamp.setSuperficie(1500.0);
        newChamp.setFerme(ferme);

        // When
        boolean result = champService.isSuperficieExceedingLimit(newChamp);

        // Then
        assertTrue(result, "Should return true when total superficie (5500) exceeds ferme superficie (5000)");
    }


    @Test
    void isSuperficieExceedingLimit_shouldHandleNullChampsList() {
        // Given
        Ferme ferme = new Ferme();
        ferme.setSuperficie(5000.0);
        ferme.setChamps(null);

        Champ newChamp = new Champ();
        newChamp.setSuperficie(2000.0);
        newChamp.setFerme(ferme);

        // When
        boolean result = champService.isSuperficieExceedingLimit(newChamp);

        // Then
        assertFalse(result, "Should return false when there are no existing champs and new champ is within limit");
    }


    @Test
    void shouldThrowException_whenChampCountIsGreaterOrEqualToTen() {
        // Mock the countByFerme method to return 10
        when(champService.countByFerme(ferme)).thenReturn(10);

        // Then
        assertThrows(TooManyChampsException.class,
                () -> champService.validateChampCount(ferme),
                "Should throw TooManyChampsException when champ count is 10");
    }

    @Test
    void shouldNotThrowException_whenChampCountIsLessThanTen() {
        // Mock the countByFerme method to return 9
        when(champService.countByFerme(ferme)).thenReturn(9);

        // Then
        assertDoesNotThrow(() -> champService.validateChampCount(ferme),
                "Should not throw exception when champ count is less than 10");
    }

    @Test
    void validateChampSuperficie_succes() {
        assertDoesNotThrow(() -> champService.validateChampSuperficie(champ, ferme),
                "Should not throw exception when champ superficie is less than 50% of ferme");
    }

    @Test
    void validateChampSuperficie_false() {

        champ.setSuperficie(5000.0);
        // When/Then
        assertThrows(ChampMustUnderException.class,
                () -> champService.validateChampSuperficie(champ, ferme),
                "Should throw ChampMustUnderException when champ superficie exceeds 50% of ferme");
    }

//    @Test
//    void findById_succes() {
//        // Arrange
//        when(champService.findById(champ.getId())).thenReturn(Optional.of(champ));
//
//        // Act
//        Optional<Champ> result = champService.findById(champ.getId());
//
//        // Assert
//        assertTrue(result.isPresent(), "Result should be present");
//        assertEquals(champ, result.get(), "The returned champ should match the mocked champ");
//
//        // Log a message for test clarity
//        System.out.println("Test findById_succes passed successfully!");
//    }

//    @Test
//    void findById_notFound() {
////        Mock
//        when(champRepository.findById(champ.getId())).thenReturn(Optional.empty());
//
//        // Action
//        assertThrows(ChampUndefinedException.class, () -> champService.findById(champ.getId()));
//    }

    @Test
    void delete_succes() {
        champ.setArbres(List.of(arbre1, arbre2));

        when(champRepository.findById(champ.getId())).thenReturn(Optional.of(champ));

        champService.delete(champ.getId());

        verify(champRepository).findById(champ.getId());
        verify(arbreService).delete(arbre1.getId());
        verify(arbreService).delete(arbre2.getId());
        verify(champRepository).delete(champ);
    }

    @Test
    void update_Success() {
        UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        // Mock
        when(champRepository.findById(id)).thenReturn(Optional.of(champ));
        when(fermeService.findByNom(champDTO.getFerme())).thenReturn(Optional.of(ferme));
        when(champRepository.findByNom(champDTO.getNom())).thenReturn(Optional.empty());
        when(champRepository.save(any(Champ.class))).thenReturn(champ);
        when(champMapper.toDTO(any(Champ.class))).thenReturn(champDTO);

        // When
        ChampDTO result = champService.update(id, champDTO);

        // Then
        assertNotNull(result);
        verify(champRepository).findById(id);
        verify(fermeService).findByNom(champDTO.getFerme());
        verify(champRepository).findByNom(champDTO.getNom());
        verify(champRepository).save(any(Champ.class));
        verify(champMapper).toDTO(any(Champ.class));
    }
}
