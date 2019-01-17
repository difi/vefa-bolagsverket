package no.difi.bolagsverket.service;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.response.BusinessInformation;
import no.difi.bolagsverket.response.dto.BusinessInformationDto;
import no.difi.bolagsverket.response.schema.Foretagsinformation;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BusinessInformationServiceImpl implements BusinessInformationService {

    private final BolagsverketClient client;
    private final IdentifierValidatorService identifierValidator;

    public BusinessInformationServiceImpl(BolagsverketClient client, IdentifierValidatorServiceImpl identifierValidator) {
        this.client = client;
        this.identifierValidator = identifierValidator;
    }

    @Override
    public BusinessInformation getBusinessInformation(String identifier) {
        log.info("Getting business information for identifier '{}'", identifier);
        boolean isValid = identifierValidator.validate(identifier);
        Optional<GetProduktResponse> clientResponse = client.getProdukt(identifier);
        if (!clientResponse.isPresent()) {
            log.warn("No response received from client.");
            return new BusinessInformation(isValid, null);
        }
        String businessName = null;
        String encodedResult = clientResponse.get().getGetProduktReturn();
        if (!Strings.isNullOrEmpty(encodedResult)) {
            log.info("Response received from Bolagsverket.");
            log.debug("Encoded response: {}", encodedResult);
            String decoded = new String(Base64.getDecoder().decode(encodedResult), Charsets.ISO_8859_1);
            log.debug("Decoded response: {}", decoded);
            Foretagsinformation businessInfo = unmarshalForetagsinformation(decoded);
            List<Foretagsinformation.Foretag> companies = businessInfo.getForetag();
            if (null != companies && !companies.isEmpty()) {
                log.info("Retrieving business information from response.");
                businessName = companies.get(0).getForetagshuvud().getFirma().getNamn();
            }
        }
        return new BusinessInformation(isValid, !Strings.isNullOrEmpty(businessName)
                ? new BusinessInformationDto(businessName)
                : null);
    }

    private Foretagsinformation unmarshalForetagsinformation(String decoded) {
        try {
            log.info("Unmarshalling Bolagsverket response.");
            JAXBContext context = JAXBContext.newInstance(Foretagsinformation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Foretagsinformation) unmarshaller.unmarshal(new StringReader(decoded));
        } catch (JAXBException e) {
            log.error("Response unmarshalling failed.", e);
            return null;
        }
    }
}
