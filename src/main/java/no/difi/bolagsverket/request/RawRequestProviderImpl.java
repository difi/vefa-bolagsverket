package no.difi.bolagsverket.request;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.schema.Foretagsfraga;
import no.difi.bolagsverket.schema.IdType;
import no.difi.bolagsverket.schema.InformationshuvudType;
import no.difi.bolagsverket.schema.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.io.StringWriter;
import java.util.GregorianCalendar;
import java.util.Objects;

@Slf4j
class RawRequestProviderImpl implements RequestProvider {

    private final ObjectFactory objectFactory = new ObjectFactory();
    private static final String PRODUKT_VERSION = "2.00";
    private static final String PRODUKT_NAME = "F1Grundpaket";

    @Override
    public String getRequest(String identifier) {
        Objects.requireNonNull(identifier);
        Foretagsfraga query = getForetagsfraga(identifier);

        return null != query ? serializeQuery(query) : null;
    }

    private String serializeQuery(Foretagsfraga query) {
        String serializedQuery = null;
        try (StringWriter writer = new StringWriter()) {
            JAXBContext context = JAXBContext.newInstance(Foretagsfraga.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(query, writer);
            serializedQuery = writer.toString();
        } catch (JAXBException e) {
            log.error("XML serialization failed.", e);
        } catch (IOException e) {
            log.error("Foretagsfraga serialization failed.", e);
        }
        return serializedQuery;
    }

    private Foretagsfraga getForetagsfraga(String identifier) {
        Foretagsfraga query = objectFactory.createForetagsfraga();
        InformationshuvudType header = getInformationshuvudType();
        query.setInformationshuvud(header);
        Foretagsfraga.Produkt produkt = getProdukt(identifier);
        query.setProdukt(produkt);

        return query;
    }

    private Foretagsfraga.Produkt getProdukt(String identifier) {
        Foretagsfraga.Produkt produkt = objectFactory.createForetagsfragaProdukt();
        produkt.setVersion(PRODUKT_VERSION);
        produkt.setNamn(PRODUKT_NAME);
        Foretagsfraga.Produkt.Sokbegrepp queryTerm = objectFactory.createForetagsfragaProduktSokbegrepp();
        IdType idType = objectFactory.createIdType();
        idType.setOrganisationsnummer(identifier);
        queryTerm.setForetagsidentitet(idType);
        produkt.getSokbegrepp().add(queryTerm);
        return produkt;
    }

    private InformationshuvudType getInformationshuvudType() {
        InformationshuvudType header = objectFactory.createInformationshuvudType();
        try {
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            XMLGregorianCalendar requestTime = datatypeFactory.newXMLGregorianCalendar(new GregorianCalendar());
            header.setMeddelandeDatumTid(requestTime);
        } catch (DatatypeConfigurationException e) {
            log.error("Failed to set request time.", e);
        }
        return header;
    }
}
