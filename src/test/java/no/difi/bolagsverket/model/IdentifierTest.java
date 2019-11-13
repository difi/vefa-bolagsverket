package no.difi.bolagsverket.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class IdentifierTest {

    @Test
    public void from_OrganizationNumber_ShouldReturnIdentifier() {
        Identifier result = Identifier.from("2021005489");
        assertNull(result.getCentury());
        assertEquals("2021005489", result.getOrganizationNumber());
    }

    @Test
    public void from_ValidCenturyPrefix_ShouldReturnIdentifier() {
        Identifier result = Identifier.from("194910017930");
        assertEquals("19", result.getCentury());
        assertEquals("4910017930", result.getOrganizationNumber());
    }

    @Test(expected = NullPointerException.class)
    public void from_NullIdentifier_ShouldThrow() {
        Identifier.from(null);
    }
}
