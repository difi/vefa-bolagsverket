package no.difi.bolagsverket.service;

import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.client.ClientException;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BolagsverketValidatorServiceImplTest {

    private BolagsverketValidatorServiceImpl target;

    @Mock
    private BolagsverketClient clientMock;
    @Mock
    private IdentifierValidatorServiceImpl identifierValidatorMock;

    @Before
    public void setUp() {
        target = new BolagsverketValidatorServiceImpl(clientMock, identifierValidatorMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_clientArgumentIsNull_shouldThrow() {
        target = new BolagsverketValidatorServiceImpl(null, identifierValidatorMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_idValidatorArgumentIsNull_shouldThrow() {
        target = new BolagsverketValidatorServiceImpl(clientMock, null);
    }

    @Test(expected = NullPointerException.class)
    public void testValidate_nullArgument_shouldThrow() {
        target.validate(null);
    }

    @Test
    public void testValidate_idValidationFailsAndClientReturnsNonNullEvenThoughNotRelevant_shouldFail() throws ClientException {
        when(identifierValidatorMock.validate(anyString())).thenReturn(false);
        when(clientMock.getProdukt(anyString())).thenReturn(mock(GetProduktResponse.class));
        assertFalse(target.validate("invalidId"));
    }

    @Test
    public void testValidate_idValidationPassesAndClientReturnsNull_shouldReturnFalse() throws ClientException {
        when(identifierValidatorMock.validate(anyString())).thenReturn(true);
        when(clientMock.getProdukt(anyString())).thenReturn(null);
        boolean result = target.validate("notFoundNumber");
        assertFalse(result);
    }

    @Test
    public void testValidate_idValidationPassesAndClientReturnsNonNullEvenThoughNotRelevant_shouldFail() throws ClientException {
        when(identifierValidatorMock.validate(anyString())).thenReturn(true);
        when(clientMock.getProdukt(anyString())).thenReturn(mock(GetProduktResponse.class));
        assertTrue(target.validate("invalidId"));
    }

}