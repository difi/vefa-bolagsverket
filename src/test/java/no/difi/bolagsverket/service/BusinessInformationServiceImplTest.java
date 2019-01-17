package no.difi.bolagsverket.service;

import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.response.BusinessInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BusinessInformationServiceImplTest {

    @InjectMocks
    private BusinessInformationServiceImpl target;
    @Mock
    private BolagsverketClient clientMock;
    @Mock
    private IdentifierValidatorServiceImpl validatorServiceMock;

    @Test
    public void getBusinessInformation_IdentifierIsNullAndNoResponseFromClient_ShouldReturnInvalidResponse() {
        when(validatorServiceMock.validate(anyString())).thenReturn(false);
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.empty());

        BusinessInformation result = target.getBusinessInformation("invalidIdentifier");

        assertFalse(result.isValidIdentifier());
        assertNull(result.getDto());
    }

    @Test
    public void getBusinessInformation_IdentifierValid_ShouldReturnEmptyBusinessInformation() {
        when(validatorServiceMock.validate(anyString())).thenReturn(true);
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.empty());

        BusinessInformation result = target.getBusinessInformation("invalidIdentifier");

        assertTrue(result.isValidIdentifier());
        assertNull(result.getDto());
    }
}
