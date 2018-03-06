package no.difi.bolagsverket.service;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.client.BolagsverketClient;

import java.util.Objects;

@Slf4j
public class BolagsverketValidatorServiceImpl implements ValidatorService {

    private final BolagsverketClient client;
    private final IdentifierValidatorImpl identifierValidator;

    public BolagsverketValidatorServiceImpl(BolagsverketClient client) {
        this.client = Objects.requireNonNull(client);
        this.identifierValidator = new IdentifierValidatorImpl();
    }

    @Override
    public boolean validate(String identifier) {
        log.info(String.format("Validating identifier %s", identifier));
        if (!identifierValidator.validate(identifier)) {
            log.info(String.format("Identifier validation failed."));
            return false;
        }
        return false;
    }
}
