package no.difi.bolagsverket.request;

import java.util.Base64;
import java.util.Objects;

public class Base64RequestProviderImpl implements RequestProvider {

    @Override
    public String getRequest(String identifier) {
        Objects.requireNonNull(identifier);
        RawRequestProviderImpl rawProvider = new RawRequestProviderImpl();
        String rawRequest = rawProvider.getRequest(identifier);
        return Base64.getEncoder().encodeToString(rawRequest.getBytes());
    }
}
