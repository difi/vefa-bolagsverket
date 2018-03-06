package no.difi.bolagsverket.request;

import java.util.Base64;
import java.util.Objects;

public class Base64RequestProviderImpl implements RequestProvider {

    @Override
    public String getRequest(String organizationNumber) {
        Objects.requireNonNull(organizationNumber);
        RawRequestProviderImpl rawProvider = new RawRequestProviderImpl();
        String rawRequest = rawProvider.getRequest(organizationNumber);
        return Base64.getEncoder().encodeToString(rawRequest.getBytes());
    }
}
