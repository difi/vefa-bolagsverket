package no.difi.bolagsverket.request;

import java.util.Optional;

public interface RequestProvider {

    Optional<String> getRequest(String identifier);
}
