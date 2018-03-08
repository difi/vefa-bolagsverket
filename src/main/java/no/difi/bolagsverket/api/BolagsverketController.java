package no.difi.bolagsverket.api;

import no.difi.bolagsverket.service.ValidatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class BolagsverketController {

    private final ValidatorService validatorService;

    public BolagsverketController(ValidatorService validatorService) {
        this.validatorService = Objects.requireNonNull(validatorService);
    }

    @RequestMapping(value = "/identifier/{id}", method = RequestMethod.GET)
    public Object getIdentifier(@PathVariable String id) {
        boolean isValid = validatorService.validate(id);
        HttpStatus status = isValid ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Void>(status);
    }
}
