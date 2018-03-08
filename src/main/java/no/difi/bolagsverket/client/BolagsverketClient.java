package no.difi.bolagsverket.client;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.config.ClientProperties;
import no.difi.bolagsverket.request.RequestProvider;
import no.difi.bolagsverket.xml.GetProdukt;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Objects;

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

    public GetProduktResponse getProdukt(String organizationNumber) {
        Objects.requireNonNull(organizationNumber);
        GetProdukt request = new GetProdukt();
        request.setCertId(properties.getCertId());
        request.setUserId(properties.getUserId());
        String xmlQuery = requestProvider.getRequest(organizationNumber);
        request.setXmlFraga(xmlQuery);
        log.info("Getting response.");
        return (GetProduktResponse) template.marshalSendAndReceive(request);
    }

}
