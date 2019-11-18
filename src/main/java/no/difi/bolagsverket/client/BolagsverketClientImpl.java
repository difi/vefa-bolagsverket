package no.difi.bolagsverket.client;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.config.ClientProperties;
import no.difi.bolagsverket.model.Identifier;
import no.difi.bolagsverket.request.RequestProvider;
import no.difi.bolagsverket.xml.GetProdukt;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Objects;
import java.util.Optional;

@Slf4j
public class BolagsverketClientImpl implements BolagsverketClient {

    private final ClientProperties properties;
    private final WebServiceTemplate template;
    private final RequestProvider requestProvider;

    public BolagsverketClientImpl(ClientProperties properties, WebServiceTemplate template, RequestProvider requestProvider) {
        this.properties = Objects.requireNonNull(properties);
        this.template = Objects.requireNonNull(template);
        this.requestProvider = Objects.requireNonNull(requestProvider);
    }

    @Override
    public Optional<GetProduktResponse> getProdukt(Identifier identifier) {
        Objects.requireNonNull(identifier);
        log.info("Requesting information from Bolagsverket for identifier '{}'.", identifier);
        GetProdukt request = new GetProdukt();
        request.setCertId(properties.getCertId());
        request.setUserId(properties.getUserId());
        Optional<String> xmlQuery = requestProvider.getRequest(identifier);
        if (!xmlQuery.isPresent()) {
            log.error("Failed to create Bolagsverket request.");
            return Optional.empty();
        }
        request.setXmlFraga(xmlQuery.get());
        return Optional.ofNullable((GetProduktResponse) template.marshalSendAndReceive(request));
    }

}
