package io.github.arch2be.genderly.gender;

import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmResponse;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmVariant;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmNotImplemented;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmVariantNotFound;
import io.github.arch2be.genderly.gender.exceptions.GenderTokenNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/gender")
class GenderController {

    private final GenderService genderService;

    GenderController(GenderService genderService) {
        this.genderService = genderService;
    }

    @GetMapping
    private GenderAlgorithmResponse guessGender(@RequestParam GenderAlgorithmVariant algorithmVariant,
                                                @RequestParam String namesToCheck) {
        return genderService.guessGender(algorithmVariant, namesToCheck);
    }

    @GetMapping(path = "/names")
    private List<String> getAllAvailableNamesGroupedByGenders() {
        return new ArrayList<>();
    }

    @ExceptionHandler({GenderAlgorithmNotImplemented.class})
    private ResponseEntity<String> handleGenderAlgorithmNotImplementedException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({GenderAlgorithmVariantNotFound.class, GenderTokenNotFound.class})
    private ResponseEntity<String> handleNotFoundExceptions(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
