package no.difi.bolagsverket.request;

import no.difi.bolagsverket.schema.Foretagsfraga;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.opensaml.xml.util.Base64;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class Base64RequestProviderImplTest {

    private Base64RequestProviderImpl target;
    private static final String BOLAGSVERKET_IDENTIFIER = "2021005489";

    @Before
    public void setUp() {
        target = new Base64RequestProviderImpl();
    }

    @Test(expected = NullPointerException.class)
    public void testGetRequest_organizationNumberIsNull_shouldThrow() {
        getRequestForIdentifier(null);
    }

    @Test
    public void testGetRequest_organizationNumberIsValid_decodedRequestShouldMatchSample() throws JAXBException {
        Optional<String> result = getRequestForIdentifier(BOLAGSVERKET_IDENTIFIER);
        byte[] decodedResult = Base64.decode(result.get());
        Foretagsfraga decodedQuery = unmarshallGetForetagsfraga(new ByteArrayInputStream(decodedResult));
        List<Foretagsfraga.Produkt.Sokbegrepp> decodedTerms = decodedQuery.getProdukt().getSokbegrepp();
        String decodedIdentifier = decodedTerms.get(0).getForetagsidentitet().getOrganisationsnummer();

        assertEquals(BOLAGSVERKET_IDENTIFIER, decodedIdentifier);
    }

    private Foretagsfraga unmarshallGetForetagsfraga(ByteArrayInputStream inputStream) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Foretagsfraga.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Foretagsfraga) unmarshaller.unmarshal(inputStream);
    }

    private Optional<String> getRequestForIdentifier(String identifier) {
        return target.getRequest(identifier);
    }

}
