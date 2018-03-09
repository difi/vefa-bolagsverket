package no.difi.bolagsverket.client;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.config.ClientProperties;
import no.difi.bolagsverket.request.RequestProvider;
import no.difi.bolagsverket.xml.GetProdukt;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Objects;
import java.util.Optional;

@Slf4j
public class BolagsverketClient {

    private final ClientProperties properties;
    private final WebServiceTemplate template;
    private final RequestProvider requestProvider;

    public BolagsverketClient(ClientProperties properties, WebServiceTemplate template, RequestProvider requestProvider) {
        this.properties = Objects.requireNonNull(properties);
        this.template = Objects.requireNonNull(template);
        this.requestProvider = Objects.requireNonNull(requestProvider);
    }

    public Optional<GetProduktResponse> getProdukt(String organizationNumber) {
        Objects.requireNonNull(organizationNumber);
        GetProdukt request = new GetProdukt();
        request.setCertId(properties.getCertId());
        request.setUserId(properties.getUserId());
        Optional<String> xmlQuery = requestProvider.getRequest(organizationNumber);
        if (!xmlQuery.isPresent()) {
            log.debug("Failed to create request.");
            return Optional.empty();
        }
        request.setXmlFraga(xmlQuery.get());
        log.info("Requesting information from Bolagsverket for identifier '{}'.", organizationNumber);
        return Optional.of((GetProduktResponse) template.marshalSendAndReceive(request));
    }

}
