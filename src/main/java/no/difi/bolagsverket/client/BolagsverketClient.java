package no.difi.bolagsverket.client;

import no.difi.bolagsverket.xml.GetProdukt;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class BolagsverketClient extends WebServiceGatewaySupport {

//    logger.

    public GetProduktResponse getProdukt(String id) {
        GetProdukt request = new GetProdukt();
//        request.setCertId();
//        request.setUserId();
//        request.setXmlFraga();
        return (GetProduktResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

}
