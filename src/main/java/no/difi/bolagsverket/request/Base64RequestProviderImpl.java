package no.difi.bolagsverket.request;

import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class Base64RequestProviderImpl implements RequestProvider {

    @Override
    public Optional<String> getRequest(String identifier) {
        Objects.requireNonNull(identifier);
        RawRequestProviderImpl rawProvider = new RawRequestProviderImpl();
        Optional<String> rawRequest = rawProvider.getRequest(identifier);
        if (rawRequest.isPresent()) {
            String encodedRequest = Base64.getEncoder().encodeToString(rawRequest.get().getBytes());
            return Optional.ofNullable(encodedRequest).filter(s -> !s.isEmpty());
        }
        log.debug("Request generation failed for identifier '{}'.", identifier);
        return Optional.empty();
    }
}
