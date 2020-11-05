package no.difi.bolagsverket.request;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.model.Identifier;
import no.difi.bolagsverket.request.schema.Foretagsfraga;
import no.difi.bolagsverket.request.schema.IdType;
import no.difi.bolagsverket.request.schema.InformationshuvudType;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
class RawRequestProviderImpl implements RequestProvider {

    private static final String PRODUKT_VERSION = "2.00";
    private static final String PRODUKT_NAME = "F1Grundpaket";
    private static final String SCHEMA_VERSION = "1.1";

    @Override
    public Optional<String> getRequest(Identifier identifier) {
        Objects.requireNonNull(identifier);
        log.info("Building xmlFraga for identifier '{}'.", identifier);
        String serializedQuery = serializeQuery(getForetagsfraga(identifier));
        log.debug(serializedQuery);
        return Optional.ofNullable(serializedQuery);
    }

    private String serializeQuery(Foretagsfraga query) {
        String serializedQuery = null;
        try (StringWriter writer = new StringWriter()) {
            JAXBContext context = JAXBContext.newInstance(Foretagsfraga.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(query, writer);
            serializedQuery = writer.toString();
        } catch (JAXBException e) {
            log.error("XML serialization failed.", e);
        } catch (IOException e) {
            log.error("Foretagsfraga serialization failed.", e);
        }
        return serializedQuery;
    }

    private Foretagsfraga getForetagsfraga(Identifier identifier) {
        InformationshuvudType header = getInformationshuvudType();
        Foretagsfraga.Produkt produkt = getProdukt(identifier);
        Foretagsfraga foretagsfraga = new Foretagsfraga();
        foretagsfraga.setInformationshuvud(header);
        foretagsfraga.setProdukt(produkt);
        foretagsfraga.setSchemaVersion(SCHEMA_VERSION);
        return foretagsfraga;
    }

    private Foretagsfraga.Produkt getProdukt(Identifier identifier) {
        BigInteger century = null != identifier.getCentury()
                ? new BigInteger(identifier.getCentury())
                : null;
        IdType idType = new IdType();
        idType.setSekel(century);
        idType.setOrganisationsnummer(identifier.getOrganizationNumber());
        Foretagsfraga.Produkt.Sokbegrepp sokbegrepp = new Foretagsfraga.Produkt.Sokbegrepp();
        sokbegrepp.setForetagsidentitet(idType);
        Foretagsfraga.Produkt produkt = new Foretagsfraga.Produkt();
        produkt.setVersion(PRODUKT_VERSION);
        produkt.setNamn(PRODUKT_NAME);
        produkt.getSokbegrepp().add(sokbegrepp);
        return produkt;
    }

    private InformationshuvudType getInformationshuvudType() {
        XMLGregorianCalendar requestTime = null;
        try {
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            requestTime = datatypeFactory.newXMLGregorianCalendar(new GregorianCalendar());
        } catch (DatatypeConfigurationException e) {
            log.error("Failed to set request time.", e);
        }
        InformationshuvudType informationshuvudType = new InformationshuvudType();
        if (null != requestTime) {
            informationshuvudType.setMeddelandeDatumTid(requestTime);
        }
        return informationshuvudType;
    }
}
