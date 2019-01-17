package no.difi.bolagsverket.response;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import no.difi.bolagsverket.response.dto.BusinessInformationDto;

/**
 * Internal response object.
 */
@Data
@RequiredArgsConstructor
public class BusinessInformation {

    @NonNull
    private final boolean validIdentifier;
    private final BusinessInformationDto dto;
}
