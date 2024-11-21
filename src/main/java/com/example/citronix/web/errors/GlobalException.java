package com.example.citronix.web.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

    @ExceptionHandler(FermeAlreadyExistsException.class)
    public String fermeAlreadyExistsException(FermeAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TooManyChampsException.class)
    public String tooManyChampsException(TooManyChampsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(SuperficieException.class)
    public String superficieException(SuperficieException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DateOfFermeException.class)
    public String dateOfFermeException(DateOfFermeException ex) {
        return ex.getMessage();
    }

    //    ------------------------
    @ExceptionHandler(ChampAlreadyExistsException.class)
    public String champAlreadyExistsException(ChampAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ChampUndefinedException.class)
    public String champUndefinedException(ChampUndefinedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ChampMustUnderException.class)
    public String champMustUnderException(ChampMustUnderException ex) {
        return ex.getMessage();
    }

    //------------------------------


    //------------------------------
    @ExceptionHandler(InvalidRecolteException.class)
    public String invalidRecolteException(InvalidRecolteException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DuplicateRecolteException.class)
    public String duplicateRecolteException(DuplicateRecolteException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ArbreUndefinedException.class)
    public String arbreUndefinedException(ArbreUndefinedException ex) {
        return ex.getMessage();
    }


    //    --------
    @ExceptionHandler(RecolteUndefinedException.class)
    public String recolteUndefinedException(RecolteUndefinedException ex) {
        return ex.getMessage();
    }

    //    --------
    @ExceptionHandler(RecoltesDetailsUndefinedException.class)
    public String recoltesDetailsUndefinedException(RecoltesDetailsUndefinedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MaximumArbreForFermeException.class)
    public String maximumArbreForFermeException(MaximumArbreForFermeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ArbrePlantationMonthException.class)
    public String arbrePlantationMonthException(ArbrePlantationMonthException ex) {
        return ex.getMessage();
    }

    //    ----------------------- Vente
    @ExceptionHandler(VenteUndefinedException.class)
    public String venteUndefinedException(VenteUndefinedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(VenteAlreadyExistException.class)
    public String venteAlreadyExistException(VenteUndefinedException ex) {
        return ex.getMessage();
    }
}
