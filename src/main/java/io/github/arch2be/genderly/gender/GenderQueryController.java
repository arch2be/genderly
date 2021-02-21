package io.github.arch2be.genderly.gender;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/gender")
class GenderQueryController {

    @GetMapping(path = "/guess")
    private String guessGenderBySpecifyAlgorithmAndProvidedName(@RequestParam GenderAlgorithmVariant algorithmVariant,
                                                                @RequestParam String nameToCheck) {
        return "Hello!";
    }

    @GetMapping(path = "/names")
    private List<String> getAllAvailableNamesGroupedByGenders() {
        return new ArrayList<>();
    }
}
