package io.github.arch2be.genderly.gender;

import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmResponse;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmVariant;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmNotImplemented;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmVariantNotFound;
import io.github.arch2be.genderly.gender.exceptions.GenderTokenNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

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
    private List<GenderTokenDto> getAllAvailableNamesGroupedByGenders(@RequestParam Integer pageSize,
                                                              @RequestParam Integer pageNumber) {
        return genderService.getPageOfEachAvailableNames(pageSize, pageNumber);
    }

    @ExceptionHandler({GenderAlgorithmNotImplemented.class})
    private ResponseEntity<String> handleGenderAlgorithmNotImplementedException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({GenderAlgorithmVariantNotFound.class, GenderTokenNotFound.class})
    private ResponseEntity<String> handleNotFoundExceptions(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    private ResponseEntity<String> handleMissingServletRequestParameterExceptionForRequiredRequestParams(ServletRequestBindingException requestBindingException) {
        return new ResponseEntity<>(requestBindingException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
