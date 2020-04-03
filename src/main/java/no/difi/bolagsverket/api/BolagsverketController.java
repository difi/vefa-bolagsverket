package no.difi.bolagsverket.api;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.model.Identifier;
import no.difi.bolagsverket.response.BusinessInformation;
import no.difi.bolagsverket.service.BusinessInformationService;
import no.difi.bolagsverket.service.IdentifierValidatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
public class BolagsverketController {

    private final IdentifierValidatorService identifierValidatorService;
    private final BusinessInformationService businessInformationService;

    public BolagsverketController(IdentifierValidatorService identifierValidatorService, BusinessInformationService businessInformationService) {
        this.identifierValidatorService = Objects.requireNonNull(identifierValidatorService);
        this.businessInformationService = Objects.requireNonNull(businessInformationService);
    }

    @GetMapping(value = "/identifier/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BusinessInformation> lookupIdentifier(@PathVariable String id) {
        try {
            log.info("Validating identifier '{}'.", id);
            if (!identifierValidatorService.validate(id)) {
                log.info("Invalid identifier provided.");
                return ResponseEntity.badRequest().build();
            }
            Identifier identifier = Identifier.from(id);
            log.info("Looking up identifier '{}'.", identifier);
            BusinessInformation businessInformation = businessInformationService.getBusinessInformation(identifier);
            if (null == businessInformation) {
                log.info("No business information available in Bolagsverket for the provided identifier.");
                return ResponseEntity.notFound().build();
            }
            log.info("Business information successfully retrieved from Bolagsverket.");
            return ResponseEntity.ok(businessInformation);
        } catch (Exception e) {
            log.error("Exception occurred when looking up identifier: {}.", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
