package no.difi.bolagsverket.service;

import no.difi.bolagsverket.response.BusinessInformation;

public interface BusinessInformationService {
    BusinessInformation getBusinessInformation(String identifier);
}
