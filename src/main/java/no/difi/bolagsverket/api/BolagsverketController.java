package no.difi.bolagsverket.api;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.response.BusinessInformation;
import no.difi.bolagsverket.response.dto.BusinessInformationDto;
import no.difi.bolagsverket.service.BusinessInformationService;
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

    private final BusinessInformationService businessInformationService;

    public BolagsverketController(BusinessInformationService businessInformationService) {
        this.businessInformationService = Objects.requireNonNull(businessInformationService);
    }

    @GetMapping(value = "/identifier/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BusinessInformationDto> lookupIdentifier(@PathVariable String id) {
        log.info("Looking up identifier '{}'.", id);
        try {
            BusinessInformation businessInformation = businessInformationService.getBusinessInformation(id);
            if (!businessInformation.isValidIdentifier()) {
                log.info("Invalid identifier provided.");
                return ResponseEntity.badRequest().build();
            }
            if (null == businessInformation.getDto()) {
                log.info("Valid identifier provided, but no business information available in Bolagsverket.");
                return ResponseEntity.notFound().build();
            }
            log.info("Valid identifier provided, and business information retrieved from Bolagsverket.");
            return ResponseEntity.ok(businessInformation.getDto());
        } catch (Exception e) {
            log.error("Exception occurred when looking up identifier.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
