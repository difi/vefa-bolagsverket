package no.difi.bolagsverket.client;

import no.difi.bolagsverket.xml.GetProduktResponse;

import java.util.Optional;

public interface BolagsverketClient {
    Optional<GetProduktResponse> getProdukt(String organizationNumber);
}
