package no.difi.bolagsverket.request;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.schema.Foretagsfraga;
import no.difi.bolagsverket.schema.IdType;
import no.difi.bolagsverket.schema.InformationshuvudType;

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
import java.util.Optional;

@Slf4j
class RawRequestProviderImpl implements RequestProvider {

    private static final String PRODUKT_VERSION = "2.00";
    private static final String PRODUKT_NAME = "F1Grundpaket";

    @Override
    public Optional<String> getRequest(String identifier) {
        Objects.requireNonNull(identifier);
        String serializedQuery = serializeQuery(getForetagsfraga(identifier));
        return null != serializedQuery
                ? Optional.of(serializedQuery)
                : Optional.empty();
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
        InformationshuvudType header = getInformationshuvudType();
        Foretagsfraga.Produkt produkt = getProdukt(identifier);
        return Foretagsfraga.builder()
                .withInformationshuvud(header)
                .withProdukt(produkt)
                .build();
    }

    private Foretagsfraga.Produkt getProdukt(String identifier) {
        Foretagsfraga.Produkt.Sokbegrepp queryTerm = Foretagsfraga.Produkt.Sokbegrepp.builder()
                .withForetagsidentitet(IdType.builder().withOrganisationsnummer(identifier).build())
                .build();
        return Foretagsfraga.Produkt.builder()
                .withVersion(PRODUKT_VERSION)
                .withNamn(PRODUKT_NAME)
                .withSokbegrepp(queryTerm)
                .build();
    }

    private InformationshuvudType getInformationshuvudType() {
        XMLGregorianCalendar requestTime = null;
        try {
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            requestTime = datatypeFactory.newXMLGregorianCalendar(new GregorianCalendar());
        } catch (DatatypeConfigurationException e) {
            log.error("Failed to set request time.", e);
        }
        return null != requestTime
                ? InformationshuvudType.builder().withMeddelandeDatumTid(requestTime).build()
                : InformationshuvudType.builder().build();
    }
}
