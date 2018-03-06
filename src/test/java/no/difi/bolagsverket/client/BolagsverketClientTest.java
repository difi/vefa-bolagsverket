package no.difi.bolagsverket.client;

import no.difi.bolagsverket.config.ClientProperties;
import no.difi.bolagsverket.request.RequestProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

@RunWith(MockitoJUnitRunner.class)
public class BolagsverketClientTest {

    private BolagsverketClient target;
    @Mock
    private WebServiceTemplate templateMock;
    @Mock
    private ClientProperties propertiesMock;
    @Mock
    private RequestProvider requestProviderMock;

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
}
