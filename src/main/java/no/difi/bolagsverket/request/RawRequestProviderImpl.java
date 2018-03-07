package no.difi.bolagsverket.request;

import no.difi.bolagsverket.schema.InformationshuvudType;
import no.difi.bolagsverket.schema.ObjectFactory;

import java.util.Objects;

class RawRequestProviderImpl implements RequestProvider {

    @Override
    public String getRequest(String organizationNumber) {
        Objects.requireNonNull(organizationNumber);

        ObjectFactory objectFactory = new ObjectFactory();

        InformationshuvudType header = objectFactory.createInformationshuvudType();
        return "";
    }
}
