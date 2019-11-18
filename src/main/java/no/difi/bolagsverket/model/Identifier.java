package no.difi.bolagsverket.model;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class Identifier {

    private String century;
    private String organizationNumber;

    private Identifier() {
    }

    public static Identifier from(String id) {
        Objects.requireNonNull(id);
        String idWithoutNonNumericCharacters = id.replaceAll("[^0-9]", "");
        String century = null;
        String organizationNumber = idWithoutNonNumericCharacters;
        if (12 <= idWithoutNonNumericCharacters.length()) {
            century = idWithoutNonNumericCharacters.substring(0, 2);
            organizationNumber = idWithoutNonNumericCharacters.substring(2);
        }
        Identifier identifier = new Identifier();
        identifier.century = century;
        identifier.organizationNumber = organizationNumber;
        return identifier;
    }
}
