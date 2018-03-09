package no.difi.bolagsverket.service;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.xml.GetProduktResponse;

import java.util.Objects;
import java.util.Optional;

@Slf4j
public class BolagsverketValidatorServiceImpl implements ValidatorService {

    private final BolagsverketClient client;
    private final IdentifierValidatorServiceImpl identifierValidator;

    public BolagsverketValidatorServiceImpl(BolagsverketClient client, IdentifierValidatorServiceImpl identifierValidator) {
        this.client = Objects.requireNonNull(client);
        this.identifierValidator = Objects.requireNonNull(identifierValidator);
    }

    @Override
    public boolean validate(String identifier) {
        Objects.requireNonNull(identifier);
        log.info("Validating identifier '{}'.", identifier);
        if (!identifierValidator.validate(identifier)) {
            log.info(String.format("Identifier validation failed: %s", identifier));
            return false;
        }

        Optional<GetProduktResponse> clientResponse = client.getProdukt(identifier);
        return clientResponse.isPresent();
    }
}
