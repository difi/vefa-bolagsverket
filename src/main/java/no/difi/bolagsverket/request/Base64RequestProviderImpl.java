package no.difi.bolagsverket.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.model.Identifier;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Base64RequestProviderImpl implements RequestProvider {

    private final RawRequestProviderImpl rawRequestProvider;

    @Override
    public Optional<String> getRequest(Identifier identifier) {
        Objects.requireNonNull(identifier);
        log.info("Building XML request for identifier '{}'.", identifier);

        Optional<String> rawRequest = rawRequestProvider.getRequest(identifier);
        if (rawRequest.isPresent()) {
            String encodedRequest = Base64.getEncoder().encodeToString(rawRequest.get().getBytes());
            log.debug("Encoded request: {}", encodedRequest);
            return Optional.ofNullable(encodedRequest).filter(s -> !s.isEmpty());
        }
        log.error("Request generation failed for identifier '{}'.", identifier);
        return Optional.empty();
    }
}
