package no.difi.bolagsverket.request;

import no.difi.bolagsverket.model.Identifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Base64RequestProviderImplTest {

    private static final Identifier BOLAGSVERKET_IDENTIFIER = Identifier.from("2021005489");

    @InjectMocks
    private Base64RequestProviderImpl target;

    @Mock
    private RawRequestProviderImpl rawRequestProviderMock;

    @Test(expected = NullPointerException.class)
    public void getRequest_OrganizationNumberIsNull_ShouldThrow() {
        target.getRequest(null);
    }

    @Test
    public void getRequest_RawRequestNotProvided_EncodedRequestShouldNotBePresent() {
        when(rawRequestProviderMock.getRequest(any(Identifier.class))).thenReturn(Optional.empty());
        assertFalse(target.getRequest(BOLAGSVERKET_IDENTIFIER).isPresent());
    }

    @Test
    public void getRequest_RawRequestReceived_EncodedRequestShouldMatchSample() {
        String rawRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<n1:Foretagsfraga xmlns=\"http://www.bolagsverket.se/schemas/common\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:n1=\"http://www.bolagsverket.se/schemas/foretagsinformation/foretagsfraga\" SchemaVersion=\"1.1\" utgar=\"1957-08-13\" xsi:schemaLocation=\"http://www.bolagsverket.se/schemas/foretagsinformation/foretagsfraga ForetagsFraga_1.1.xsd\">\n" +
                "  <n1:Informationshuvud>\n" +
                "    <Avsandare>String</Avsandare>\n" +
                "    <Sekvensnummer>String</Sekvensnummer>\n" +
                "    <MeddelandeTyp>String</MeddelandeTyp>\n" +
                "    <Referens>String</Referens>\n" +
                "    <MeddelandeDatumTid>2014-12-17T09:30:47Z</MeddelandeDatumTid>\n" +
                "    <SessionsId>String</SessionsId>\n" +
                "    <Referens2>String</Referens2>\n" +
                "  </n1:Informationshuvud>\n" +
                "  <n1:Produkt version=\"2.01\">\n" +
                "    <n1:Namn>F1Grundpaket</n1:Namn>\n" +
                "    <n1:Sokbegrepp>\n" +
                "      <n1:Foretagsidentitet>\n" +
                "        <Organisationsnummer>2021005489</Organisationsnummer>\n" +
                "      </n1:Foretagsidentitet>\n" +
                "    </n1:Sokbegrepp>\n" +
                "  </n1:Produkt>\n" +
                "</n1:Foretagsfraga>\n";
        String encodedRequest = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPG4xOkZvcmV0YWdzZnJhZ2Eg" +
                "eG1sbnM9Imh0dHA6Ly93d3cuYm9sYWdzdmVya2V0LnNlL3NjaGVtYXMvY29tbW9uIiB4bWxuczp4" +
                "c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIiB4bWxuczpuMT0i" +
                "aHR0cDovL3d3dy5ib2xhZ3N2ZXJrZXQuc2Uvc2NoZW1hcy9mb3JldGFnc2luZm9ybWF0aW9uL2Zv" +
                "cmV0YWdzZnJhZ2EiIFNjaGVtYVZlcnNpb249IjEuMSIgdXRnYXI9IjE5NTctMDgtMTMiIHhzaTpz" +
                "Y2hlbWFMb2NhdGlvbj0iaHR0cDovL3d3dy5ib2xhZ3N2ZXJrZXQuc2Uvc2NoZW1hcy9mb3JldGFn" +
                "c2luZm9ybWF0aW9uL2ZvcmV0YWdzZnJhZ2EgRm9yZXRhZ3NGcmFnYV8xLjEueHNkIj4KICA8bjE6" +
                "SW5mb3JtYXRpb25zaHV2dWQ+CiAgICA8QXZzYW5kYXJlPlN0cmluZzwvQXZzYW5kYXJlPgogICAg" +
                "PFNla3ZlbnNudW1tZXI+U3RyaW5nPC9TZWt2ZW5zbnVtbWVyPgogICAgPE1lZGRlbGFuZGVUeXA+" +
                "U3RyaW5nPC9NZWRkZWxhbmRlVHlwPgogICAgPFJlZmVyZW5zPlN0cmluZzwvUmVmZXJlbnM+CiAg" +
                "ICA8TWVkZGVsYW5kZURhdHVtVGlkPjIwMTQtMTItMTdUMDk6MzA6NDdaPC9NZWRkZWxhbmRlRGF0" +
                "dW1UaWQ+CiAgICA8U2Vzc2lvbnNJZD5TdHJpbmc8L1Nlc3Npb25zSWQ+CiAgICA8UmVmZXJlbnMy" +
                "PlN0cmluZzwvUmVmZXJlbnMyPgogIDwvbjE6SW5mb3JtYXRpb25zaHV2dWQ+CiAgPG4xOlByb2R1" +
                "a3QgdmVyc2lvbj0iMi4wMSI+CiAgICA8bjE6TmFtbj5GMUdydW5kcGFrZXQ8L24xOk5hbW4+CiAg" +
                "ICA8bjE6U29rYmVncmVwcD4KICAgICAgPG4xOkZvcmV0YWdzaWRlbnRpdGV0PgogICAgICAgIDxP" +
                "cmdhbmlzYXRpb25zbnVtbWVyPjIwMjEwMDU0ODk8L09yZ2FuaXNhdGlvbnNudW1tZXI+CiAgICAg" +
                "IDwvbjE6Rm9yZXRhZ3NpZGVudGl0ZXQ+CiAgICA8L24xOlNva2JlZ3JlcHA+CiAgPC9uMTpQcm9k" +
                "dWt0Pgo8L24xOkZvcmV0YWdzZnJhZ2E+Cg==";
        when(rawRequestProviderMock.getRequest(any(Identifier.class))).thenReturn(Optional.of(rawRequest));

        Optional<String> result = target.getRequest(BOLAGSVERKET_IDENTIFIER);

        assertTrue(result.isPresent());
        assertEquals(result.get(), encodedRequest);
    }

}
