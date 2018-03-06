package no.difi.bolagsverket.client;

import no.difi.bolagsverket.config.ClientProperties;
import no.difi.bolagsverket.xml.GetProdukt;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.springframework.ws.client.core.WebServiceTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Objects;

public class BolagsverketClient {

    //    logger.
    private final ClientProperties properties;
    private final WebServiceTemplate template;

    public BolagsverketClient(ClientProperties properties, WebServiceTemplate template) {
        this.properties = Objects.requireNonNull(properties);
        this.template = Objects.requireNonNull(template);
    }

    public GetProduktResponse getProdukt(int organizationNumber) {
        GetProdukt request = new GetProdukt();
        request.setCertId(properties.getCertId());
        request.setUserId(properties.getUserId());
//        request.setXmlFraga();
//        return (GetProduktResponse) template.marshalSendAndReceive(request);
        throw new NotImplementedException();
    }

}
