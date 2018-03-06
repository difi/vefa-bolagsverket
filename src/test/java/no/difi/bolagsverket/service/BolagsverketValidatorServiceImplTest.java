package no.difi.bolagsverket.service;

import no.difi.bolagsverket.client.BolagsverketClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BolagsverketValidatorServiceImplTest {

    private BolagsverketValidatorServiceImpl target;

    @Mock
    private BolagsverketClient clientMock;

    @Before
    public void setUp() {
        target = new BolagsverketValidatorServiceImpl(clientMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_clientArgumentIsNull_shouldThrow() {
        target = new BolagsverketValidatorServiceImpl(null);
    }

    @Test(expected = NullPointerException.class)
    public void testValidate_nullArgument_shouldThrow() {
        target.validate(null);
    }

    @Test
    public void testValidate_clientReturnsNull_shouldReturnFalse() {
        when(clientMock.getProdukt(anyString())).thenReturn(null);
        boolean result = target.validate("notFoundNumber");
        assertFalse(result);
    }
}