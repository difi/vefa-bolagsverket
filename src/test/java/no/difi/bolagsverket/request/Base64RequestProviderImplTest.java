package no.difi.bolagsverket.request;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class Base64RequestProviderImplTest {

    private Base64RequestProviderImpl target;

    @Before
    public void setUp() {
        target = new Base64RequestProviderImpl();
    }

    @Test(expected = NullPointerException.class)
    public void testGetRequest_organizationNumberIsNull_shouldThrow() {
        target.getRequest(null);
    }

    @Test
    public void testGetRequest_organizationNumberIsValid_shouldReturnNonNull() {
        String result = target.getRequest("162021005489");
        assertNotNull(result);
    }

}
