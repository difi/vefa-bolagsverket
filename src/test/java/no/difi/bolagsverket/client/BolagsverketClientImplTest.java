package no.difi.bolagsverket.client;

import no.difi.bolagsverket.config.ClientProperties;
import no.difi.bolagsverket.request.RequestProvider;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BolagsverketClientImplTest {

    private BolagsverketClient target;
    @Mock
    private WebServiceTemplate templateMock;
    @Mock
    private ClientProperties propertiesMock;
    @Mock
    private RequestProvider requestProviderMock;

    @Before
    public void setUp() {
        when(propertiesMock.getCertId()).thenReturn("validCertId");
        when(propertiesMock.getUserId()).thenReturn("validUserId");
        target = new BolagsverketClientImpl(propertiesMock, templateMock, requestProviderMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_propertiesIsNull_shouldThrow() {
        target = new BolagsverketClientImpl(null, templateMock, requestProviderMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_templateIsNull_shouldThrow() {
        target = new BolagsverketClientImpl(propertiesMock, null, requestProviderMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_requestProviderIsNull_shouldThrow() {
        target = new BolagsverketClientImpl(propertiesMock, templateMock, null);
    }

    @Test(expected = NullPointerException.class)
    public void testGetProdukt_identifierIsNull_shouldThrow() {
        target.getProdukt(null);
    }

    @Test
    public void testGetProdukt_xmlQueryIsEmpty_resultShouldBeEmpty() {
        when(requestProviderMock.getRequest(anyString())).thenReturn(Optional.empty());
        Optional<GetProduktResponse> result = target.getProdukt("someId");
        assertFalse(result.isPresent());
    }
}
