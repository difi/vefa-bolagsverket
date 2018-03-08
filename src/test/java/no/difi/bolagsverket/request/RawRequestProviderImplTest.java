package no.difi.bolagsverket.request;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RawRequestProviderImplTest {

    private RawRequestProviderImpl target;

    @Before
    public void setUp() {
        target = new RawRequestProviderImpl();
    }

    @Test(expected = NullPointerException.class)
    public void testGetRequest_organizationNumberIsNull_shouldThrow() {
        target.getRequest(null);
    }

    @Test
    public void testGetRequest_organizationNumberIsValid_shouldReturnNonNull() {
        Optional<String> result = target.getRequest("162021005489");
        assertTrue(result.isPresent());
        assertFalse(result.get().isEmpty());
    }
}
