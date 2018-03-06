package no.difi.bolagsverket.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        String result = target.getRequest("162021005489");
        Assert.assertNotNull(result);
    }
}
