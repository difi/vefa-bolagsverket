package no.difi.bolagsverket.service;

import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

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
    public void testValidate_idValidationFailsAndClientReturnsResponse_resultShouldBeFalse() {
        when(identifierValidatorMock.validate(anyString())).thenReturn(false);
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.of(mock(GetProduktResponse.class)));
        assertFalse(target.validate("invalidId"));
    }

    @Test
    public void testValidate_idValidationPassesAndClientReturnsEmpty_resultShouldBeFalse() {
        when(identifierValidatorMock.validate(anyString())).thenReturn(true);
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.empty());
        boolean result = target.validate("notFoundNumber");
        assertFalse(result);
    }

    @Test
    public void testValidate_idValidationPassesAndClientReturnsResponse_resultShouldBeTrue() {
        when(identifierValidatorMock.validate(anyString())).thenReturn(true);
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.of(mock(GetProduktResponse.class)));
        assertTrue(target.validate("invalidId"));
    }

}