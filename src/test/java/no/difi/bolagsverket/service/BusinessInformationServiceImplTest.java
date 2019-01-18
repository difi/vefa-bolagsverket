package no.difi.bolagsverket.service;

import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.response.BusinessInformation;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BusinessInformationServiceImplTest {

    @InjectMocks
    private BusinessInformationServiceImpl target;
    @Mock
    private BolagsverketClient clientMock;

    @Test
    public void getBusinessInformation_IdentifierIsNullAndNoResponseFromClient_ShouldReturnInvalidResponse() {
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.empty());
        assertNull(target.getBusinessInformation("invalidIdentifier"));
    }

    @Test
    public void getBusinessInformation_IdentifierValid_ShouldReturnEmptyBusinessInformation() {
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.empty());
        assertNull(target.getBusinessInformation("invalidIdentifier"));
    }

    @Test
    public void getBusinessInformation_BusinessInformationReceivedFromClient_ShouldDecodeLatinCharacters() {
        String encodedClientResponse = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iSVNPLTg4NTktMTUiPz4NCjxGb3JldGFnc2luZm9ybWF0aW9uIFNjaGVtYVZlcnNpb249IjIuMDAiIHhtbG5zPSJodHRwOi8vd3d3LmJvbGFnc3ZlcmtldC5zZS9zY2hlbWFzL2ZvcmV0YWdzaW5mb3JtYXRpb24vRjFfMjAwIiB4bWxuczpmb3JldGFnc2h1dnVkMjAwPSJodHRwOi8vd3d3LmJvbGFnc3ZlcmtldC5zZS9zY2hlbWFzL2NvbW1vbi9mb3JldGFnc2h1dnVkL2ZvcmV0YWdzaHV2dWRfMjAwIiB4bWxuczppZGVudGl0ZXQyMDA9Imh0dHA6Ly93d3cuYm9sYWdzdmVya2V0LnNlL3NjaGVtYXMvY29tbW9uL2lkZW50aXRldC9pZGVudGl0ZXRfMjAwIiB4bWxuczppbmZvcm1hdGlvbnNodXZ1ZDIwMD0iaHR0cDovL3d3dy5ib2xhZ3N2ZXJrZXQuc2Uvc2NoZW1hcy9jb21tb24vaW5mb3JtYXRpb25zaHV2dWQvaW5mb3JtYXRpb25zaHV2dWRfMjAwIj4NCiAgPGluZm9ybWF0aW9uc2h1dnVkMjAwOkluZm9ybWF0aW9uc2h1dnVkPg0KICAgIDxpbmZvcm1hdGlvbnNodXZ1ZDIwMDpNZWRkZWxhbmRlRGF0dW1UaWQ+MjAxOS0wMS0xN1QxNDoyMToxMi41MjUrMDE6MDA8L2luZm9ybWF0aW9uc2h1dnVkMjAwOk1lZGRlbGFuZGVEYXR1bVRpZD4NCiAgICA8aW5mb3JtYXRpb25zaHV2dWQyMDA6U3ZhcnNJbmZvcm1hdGlvbiBrb2Q9IjAiIHRleHQ9Ik9LIi8+DQogIDwvaW5mb3JtYXRpb25zaHV2dWQyMDA6SW5mb3JtYXRpb25zaHV2dWQ+DQogIDxGb3JldGFnPg0KICAgIDxmb3JldGFnc2h1dnVkMjAwOkZvcmV0YWdzaHV2dWQ+DQogICAgICA8Zm9yZXRhZ3NodXZ1ZDIwMDpGb3JldGFnc2lkZW50aXRldCBib2xhZ3N2ZXJrZXRzaWQ9IjIwMDQwMzE2MTEyMDMzNzgiIHJlZ2lzdHJlcmluZ3NkYXR1bT0iMjAwNC0wNC0yOSI+DQogICAgICAgIDxpZGVudGl0ZXQyMDA6T3JnYW5pc2F0aW9uc251bW1lcj41NTY2NjE4MDIwPC9pZGVudGl0ZXQyMDA6T3JnYW5pc2F0aW9uc251bW1lcj4NCiAgICAgIDwvZm9yZXRhZ3NodXZ1ZDIwMDpGb3JldGFnc2lkZW50aXRldD4NCiAgICAgIDxmb3JldGFnc2h1dnVkMjAwOkZpcm1hPg0KICAgICAgICA8Zm9yZXRhZ3NodXZ1ZDIwMDpOYW1uPklGSyBH9nRlYm9yZyBGb3Rib2xsIEFCPC9mb3JldGFnc2h1dnVkMjAwOk5hbW4+DQogICAgICA8L2ZvcmV0YWdzaHV2dWQyMDA6RmlybWE+DQogICAgICA8Zm9yZXRhZ3NodXZ1ZDIwMDpGb3JldGFnc2Zvcm0gdHlwPSJhYiI+YWt0aWVib2xhZzwvZm9yZXRhZ3NodXZ1ZDIwMDpGb3JldGFnc2Zvcm0+DQogICAgICA8Zm9yZXRhZ3NodXZ1ZDIwMDpJbm5lbGlnZ2FuZGVBcmFuZGVuIGFudGFsPSIzIi8+DQogICAgPC9mb3JldGFnc2h1dnVkMjAwOkZvcmV0YWdzaHV2dWQ+DQogIDwvRm9yZXRhZz4NCjwvRm9yZXRhZ3NpbmZvcm1hdGlvbj4NCg==";
        GetProduktResponse clientResponseMock = mock(GetProduktResponse.class);
        when(clientResponseMock.getGetProduktReturn()).thenReturn(encodedClientResponse);
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.of(clientResponseMock));

        BusinessInformation result = target.getBusinessInformation("5566618020");

        assertEquals("IFK Göteborg Fotboll AB", result.getName());
    }
}
