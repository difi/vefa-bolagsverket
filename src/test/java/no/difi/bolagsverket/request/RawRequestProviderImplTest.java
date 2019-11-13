package no.difi.bolagsverket.request;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.model.Identifier;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Slf4j
public class RawRequestProviderImplTest {

    private RawRequestProviderImpl target;
    private static final Identifier BOLAGSVERKET_IDENTIFIER = Identifier.from("2021005489");
    private static final Identifier ENSKILD_FIRMA_IDENTIFIER = Identifier.from("194910017930");

    @Before
    public void setUp() {
        target = new RawRequestProviderImpl();
    }

    @Test(expected = NullPointerException.class)
    public void getRequest_IdentifierIsNull_ShouldThrow() {
        target.getRequest(null);
    }

    @Test
    public void getRequest_IdentifierIsValidOrganizationNumber_ShouldSucceed() {
        Optional<String> result = target.getRequest(BOLAGSVERKET_IDENTIFIER);

        assertTrue(result.isPresent());
        String xml = result.get();
        assertFalse(xml.isEmpty());
        assertTrue(validateXml(xml));
    }

    @Test
    public void getRequest_IdentifierIsValidWithCenturyPrefix_ShouldSucceed() {
        Optional<String> result = target.getRequest(ENSKILD_FIRMA_IDENTIFIER);

        assertTrue(result.isPresent());
        String xml = result.get();
        assertFalse(xml.isEmpty());
        assertTrue(validateXml(xml));
    }

    private boolean validateXml(String xml) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File schemaFile = new ClassPathResource("/request/foretagsinformation/ForetagsFraga_1.1.xsd").getFile();
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            Source xmlSource = new StreamSource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            validator.validate(xmlSource);
        } catch (SAXException e) {
            log.warn("XML validation failed with message: {}", e.getMessage());
            return false;
        } catch (IOException e) {
            throw new IllegalStateException("The request could not be validated.", e);
        }
        return true;
    }
}
