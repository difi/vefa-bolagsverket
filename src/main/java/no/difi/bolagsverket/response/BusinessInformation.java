package no.difi.bolagsverket.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Response Object. *
 * <p>Subset of response from Bolagsverket's XML services.</p> *
 */
@Getter
@ToString
@RequiredArgsConstructor
public class BusinessInformation {

    private final String name;
}
