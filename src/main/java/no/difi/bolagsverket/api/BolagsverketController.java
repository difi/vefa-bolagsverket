package no.difi.bolagsverket.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BolagsverketController {

    @RequestMapping(value = "/identifier/{id}", method = RequestMethod.GET)
    public Object getIdentifier(@PathVariable String id) {
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
