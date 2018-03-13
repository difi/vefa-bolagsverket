package no.difi.bolagsverket.service;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.response.schema.Foretagsinformation;
import no.difi.bolagsverket.response.schema.InformationshuvudType;
import no.difi.bolagsverket.xml.GetProduktResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Base64;
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
        return clientResponse.isPresent() && validate(clientResponse.get());
    }

    private boolean validate(GetProduktResponse response) {
        String encodedResult = response.getGetProduktReturn();
        if (null == encodedResult || encodedResult.isEmpty()) {
            return false;
        }
        String decoded = new String(Base64.getDecoder().decode(encodedResult));
        Foretagsinformation businessInfo = unmarshalForetagsinformation(decoded);
        InformationshuvudType.SvarsInformation answerInfo
                = (null != businessInfo)
                ? businessInfo.getInformationshuvud().getSvarsInformation()
                : null;
        return (null != answerInfo) && answerInfo.getKod() == 0;
    }

    private Foretagsinformation unmarshalForetagsinformation(String decoded) {
        try {
            JAXBContext context = JAXBContext.newInstance(Foretagsinformation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Foretagsinformation) unmarshaller.unmarshal(new StringReader(decoded));
        } catch (JAXBException e) {
            log.error("Response decoding failed.", e);
            return null;
        }
    }
}
