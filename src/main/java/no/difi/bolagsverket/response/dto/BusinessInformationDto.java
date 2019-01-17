package no.difi.bolagsverket.response.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Data Transfer Object. *
 * <p>Subset of response from Bolagsverket's XML services.</p> *
 */
@Getter
@ToString
@RequiredArgsConstructor
public class BusinessInformationDto {

    private final String name;
}
