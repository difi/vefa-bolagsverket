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
        String century = null;
        String organizationNumber = id;
        if (12 <= id.length()) {
            century = id.substring(0, 2);
            organizationNumber = id.substring(2);
        }
        Identifier identifier = new Identifier();
        identifier.century = century;
        identifier.organizationNumber = organizationNumber;
        return identifier;
    }
}
