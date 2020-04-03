package no.difi.bolagsverket.model;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class Identifier {

    private String century;
    private String organizationNumber;
    private String serialNumber;

    private Identifier() {
    }

    public static Identifier from(String providedIdentifier) {
        Objects.requireNonNull(providedIdentifier);
        String idWithoutNonNumericCharacters = providedIdentifier.replaceAll("[^0-9]", "");
        String century = null;
        String organizationNumber = idWithoutNonNumericCharacters;
        String serialNumber = null;
        if (12 <= idWithoutNonNumericCharacters.length()) {
            century = idWithoutNonNumericCharacters.substring(0, 2);
            organizationNumber = idWithoutNonNumericCharacters.substring(2);
        }
        if (10 < organizationNumber.length()) {
            serialNumber = organizationNumber.substring(10);
            organizationNumber = organizationNumber.substring(0, 10);
        }
        Identifier identifier = new Identifier();
        identifier.century = century;
        identifier.organizationNumber = organizationNumber;
        identifier.serialNumber = serialNumber;
        return identifier;
    }
}
