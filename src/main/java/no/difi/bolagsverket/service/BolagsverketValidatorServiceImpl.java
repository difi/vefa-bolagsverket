package no.difi.bolagsverket.service;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.client.ClientException;
import no.difi.bolagsverket.xml.GetProduktResponse;

import java.util.Objects;

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
        log.info(String.format("Validating identifier %s", identifier));
        if (!identifierValidator.validate(identifier)) {
            log.info(String.format("Identifier validation failed: %s", identifier));
            return false;
        }

        GetProduktResponse clientResponse;
        try {
            clientResponse = client.getProdukt(identifier);
            return null != clientResponse;
        } catch (ClientException e) {
            throw new ServiceException("Bolagsverket client query failed.", e);
        }
    }
}
