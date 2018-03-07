package no.difi.bolagsverket.client;

import no.difi.bolagsverket.config.ClientProperties;
import no.difi.bolagsverket.request.RequestProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BolagsverketClientTest {

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
        target = new BolagsverketClient(propertiesMock, templateMock, requestProviderMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_propertiesIsNull_shouldThrow() {
        target = new BolagsverketClient(null, templateMock, requestProviderMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_templateIsNull_shouldThrow() {
        target = new BolagsverketClient(propertiesMock, null, requestProviderMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_requestProviderIsNull_shouldThrow() {
        target = new BolagsverketClient(propertiesMock, templateMock, null);
    }

    @Test(expected = NullPointerException.class)
    public void testGetProdukt_identifierIsNull_shouldThrow() throws ClientException {
        target.getProdukt(null);
    }

    @Test(expected = ClientException.class)
    public void testGetProdukt_xmlQueryIsNotProvided_shouldThrow() throws ClientException {
        when(requestProviderMock.getRequest(anyString())).thenReturn(null);
        target.getProdukt("someId");
    }
}
