package no.difi.bolagsverket.request;

import no.difi.bolagsverket.model.Identifier;

import java.util.Optional;

public interface RequestProvider {
    Optional<String> getRequest(Identifier identifier);
}
