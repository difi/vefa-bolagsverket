package no.difi.bolagsverket.service;

import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BolagsverketValidatorServiceImplTest {

    private BolagsverketValidatorServiceImpl target;

    @Mock
    private BolagsverketClient clientMock;
    @Mock
    private IdentifierValidatorServiceImpl identifierValidatorMock;

    @Before
    public void setUp() {
        target = new BolagsverketValidatorServiceImpl(clientMock, identifierValidatorMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_clientArgumentIsNull_shouldThrow() {
        target = new BolagsverketValidatorServiceImpl(null, identifierValidatorMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_idValidatorArgumentIsNull_shouldThrow() {
        target = new BolagsverketValidatorServiceImpl(clientMock, null);
    }

    @Test(expected = NullPointerException.class)
    public void testValidate_nullArgument_shouldThrow() {
        target.validate(null);
    }

    @Test
    public void testValidate_idValidationFailsAndClientReturnsResponse_resultShouldBeFalse() {
        when(identifierValidatorMock.validate(anyString())).thenReturn(false);
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.of(mock(GetProduktResponse.class)));
        assertFalse(target.validate("invalidId"));
    }

    @Test
    public void testValidate_idValidationPassesAndClientReturnsEmpty_resultShouldBeFalse() {
        when(identifierValidatorMock.validate(anyString())).thenReturn(true);
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.empty());
        boolean result = target.validate("notFoundNumber");
        assertFalse(result);
    }

    @Test
    public void testValidate_idValidationPassesAndClientReturnsInvalidResponse_resultShouldBeFalse() {
        when(identifierValidatorMock.validate(anyString())).thenReturn(true);
        GetProduktResponse responseMock = mock(GetProduktResponse.class);
        when(responseMock.getGetProduktReturn()).thenReturn("invalidResponse");
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.of(responseMock));

        assertFalse(target.validate("validId"));
    }

    @Test
    public void testValidate_idValidationPassesAndClientReturnsValidResponse_resultShouldBeTrue() throws IOException {
        final String ORGANIZATION_EXISTS_RESPONSE = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iSVNPLTg4NTktMTUiPz4NCjxGb3JldGFnc2luZm9ybWF0aW9uIFNjaGVtYVZlcnNpb249IjIuMDAiIHhtbG5zPSJodHRwOi8vd3d3LmJvbGFnc3ZlcmtldC5zZS9zY2hlbWFzL2ZvcmV0YWdzaW5mb3JtYXRpb24vRjFfMjAwIg0KICAgICAgICAgICAgICAgICAgICAgeG1sbnM6Zm9yZXRhZ3NodXZ1ZDIwMD0iaHR0cDovL3d3dy5ib2xhZ3N2ZXJrZXQuc2Uvc2NoZW1hcy9jb21tb24vZm9yZXRhZ3NodXZ1ZC9mb3JldGFnc2h1dnVkXzIwMCINCiAgICAgICAgICAgICAgICAgICAgIHhtbG5zOmlkZW50aXRldDIwMD0iaHR0cDovL3d3dy5ib2xhZ3N2ZXJrZXQuc2Uvc2NoZW1hcy9jb21tb24vaWRlbnRpdGV0L2lkZW50aXRldF8yMDAiDQogICAgICAgICAgICAgICAgICAgICB4bWxuczppbmZvcm1hdGlvbnNodXZ1ZDIwMD0iaHR0cDovL3d3dy5ib2xhZ3N2ZXJrZXQuc2Uvc2NoZW1hcy9jb21tb24vaW5mb3JtYXRpb25zaHV2dWQvaW5mb3JtYXRpb25zaHV2dWRfMjAwIj4NCiAgICA8aW5mb3JtYXRpb25zaHV2dWQyMDA6SW5mb3JtYXRpb25zaHV2dWQ+DQogICAgICAgIDxpbmZvcm1hdGlvbnNodXZ1ZDIwMDpNZWRkZWxhbmRlRGF0dW1UaWQ+MjAxOC0wMy0xM1QxMjoyOTo0My45MDErMDE6MDA8L2luZm9ybWF0aW9uc2h1dnVkMjAwOk1lZGRlbGFuZGVEYXR1bVRpZD4NCiAgICAgICAgPGluZm9ybWF0aW9uc2h1dnVkMjAwOlN2YXJzSW5mb3JtYXRpb24ga29kPSIwIiB0ZXh0PSJPSyIvPg0KICAgIDwvaW5mb3JtYXRpb25zaHV2dWQyMDA6SW5mb3JtYXRpb25zaHV2dWQ+DQogICAgPEZvcmV0YWc+DQogICAgICAgIDxmb3JldGFnc2h1dnVkMjAwOkZvcmV0YWdzaHV2dWQ+DQogICAgICAgICAgICA8Zm9yZXRhZ3NodXZ1ZDIwMDpGb3JldGFnc2lkZW50aXRldCBib2xhZ3N2ZXJrZXRzaWQ9IjAwMDAwMDU1NjQwNDU3OTAiIHJlZ2lzdHJlcmluZ3NkYXR1bT0iMTkxNS0wNS0wNSI+DQogICAgICAgICAgICAgICAgPGlkZW50aXRldDIwMDpPcmdhbmlzYXRpb25zbnVtbWVyPjU1NjQwNDU3OTA8L2lkZW50aXRldDIwMDpPcmdhbmlzYXRpb25zbnVtbWVyPg0KICAgICAgICAgICAgPC9mb3JldGFnc2h1dnVkMjAwOkZvcmV0YWdzaWRlbnRpdGV0Pg0KICAgICAgICAgICAgPGZvcmV0YWdzaHV2dWQyMDA6RmlybWE+DQogICAgICAgICAgICAgICAgPGZvcmV0YWdzaHV2dWQyMDA6TmFtbj5UZXN0Ym9sYWdldCBBQjwvZm9yZXRhZ3NodXZ1ZDIwMDpOYW1uPg0KICAgICAgICAgICAgPC9mb3JldGFnc2h1dnVkMjAwOkZpcm1hPg0KICAgICAgICAgICAgPGZvcmV0YWdzaHV2dWQyMDA6Rm9yZXRhZ3Nmb3JtIHR5cD0iYWIiPmFrdGllYm9sYWc8L2ZvcmV0YWdzaHV2dWQyMDA6Rm9yZXRhZ3Nmb3JtPg0KICAgICAgICAgICAgPGZvcmV0YWdzaHV2dWQyMDA6SW5uZWxpZ2dhbmRlQXJhbmRlbiBhbnRhbD0iMyIvPg0KICAgICAgICA8L2ZvcmV0YWdzaHV2dWQyMDA6Rm9yZXRhZ3NodXZ1ZD4NCiAgICA8L0ZvcmV0YWc+DQo8L0ZvcmV0YWdzaW5mb3JtYXRpb24+";
        when(identifierValidatorMock.validate(anyString())).thenReturn(true);
        GetProduktResponse responseMock = mock(GetProduktResponse.class);
        when(responseMock.getGetProduktReturn()).thenReturn(ORGANIZATION_EXISTS_RESPONSE);
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.of(responseMock));

        assertTrue(target.validate("validId"));
    }

    @Test
    public void testValidate_idValidationPassesAndClientReturnsUnsuccessfulResponse_resultShouldBeFalse() throws IOException {
        final String ORGANIZATION_DOES_NOT_EXIST_RESPONSE = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iSVNPLTg4NTktMTUiPz4NCjxGb3JldGFnc2luZm9ybWF0aW9uIFNjaGVtYVZlcnNpb249IjIuMDAiIHhtbG5zPSJodHRwOi8vd3d3LmJvbGFnc3ZlcmtldC5zZS9zY2hlbWFzL2ZvcmV0YWdzaW5mb3JtYXRpb24vRjFfMjAwIg0KICAgICAgICAgICAgICAgICAgICAgeG1sbnM6aW5mb3JtYXRpb25zaHV2dWQyMDA9Imh0dHA6Ly93d3cuYm9sYWdzdmVya2V0LnNlL3NjaGVtYXMvY29tbW9uL2luZm9ybWF0aW9uc2h1dnVkL2luZm9ybWF0aW9uc2h1dnVkXzIwMCI+DQogICAgPGluZm9ybWF0aW9uc2h1dnVkMjAwOkluZm9ybWF0aW9uc2h1dnVkPg0KICAgICAgICA8aW5mb3JtYXRpb25zaHV2dWQyMDA6TWVkZGVsYW5kZURhdHVtVGlkPjIwMTgtMDMtMTNUMTI6Mjk6NDMuOTAxKzAxOjAwPC9pbmZvcm1hdGlvbnNodXZ1ZDIwMDpNZWRkZWxhbmRlRGF0dW1UaWQ+DQogICAgICAgIDxpbmZvcm1hdGlvbnNodXZ1ZDIwMDpTdmFyc0luZm9ybWF0aW9uIGtvZD0iMjA1MiIgdGV4dD0iT0JKRUtUTlVNTUVSIFNBS05BUyIvPg0KICAgIDwvaW5mb3JtYXRpb25zaHV2dWQyMDA6SW5mb3JtYXRpb25zaHV2dWQ+DQo8L0ZvcmV0YWdzaW5mb3JtYXRpb24+";
        when(identifierValidatorMock.validate(anyString())).thenReturn(true);
        GetProduktResponse responseMock = mock(GetProduktResponse.class);
        when(responseMock.getGetProduktReturn()).thenReturn(ORGANIZATION_DOES_NOT_EXIST_RESPONSE);
        when(clientMock.getProdukt(anyString())).thenReturn(Optional.of(responseMock));

        assertFalse(target.validate("validId"));
    }

}