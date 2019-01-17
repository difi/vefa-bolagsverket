package no.difi.bolagsverket.api;

import no.difi.bolagsverket.response.BusinessInformation;
import no.difi.bolagsverket.response.dto.BusinessInformationDto;
import no.difi.bolagsverket.service.BusinessInformationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BolagsverketController.class)
@AutoConfigureMockMvc
public class BolagsverketControllerTest {

    private final String LOOKUP_URI_PATH = "/identifier/SwedishOrganizationNumberHere";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessInformationService businessInformationServiceMock;

    @Test
    public void lookupIdentifier_InvalidIdentifier_ShouldReturnBadRequest() throws Exception {
        BusinessInformation businessInformationMock = mockBusinessInformation(false, null);
        when(businessInformationServiceMock.getBusinessInformation(anyString())).thenReturn(businessInformationMock);
        mockMvc.perform(get(LOOKUP_URI_PATH)).andExpect(status().isBadRequest());
    }

    @Test
    public void lookupIdentifier_ValidIdentifierButNoInformationFoundInBolagsverket_ShouldReturn404() throws Exception {
        BusinessInformation businessInformationMock = mockBusinessInformation(true, null);
        when(businessInformationServiceMock.getBusinessInformation(anyString())).thenReturn(businessInformationMock);
        mockMvc.perform(get(LOOKUP_URI_PATH)).andExpect(status().isNotFound());
    }

    @Test
    public void lookupIdentifier_ValidIdentifierAndInformationFoundInBolagsverket_ShouldReturn200WithContent() throws Exception {
        BusinessInformation businessInformationMock = mockBusinessInformation(true, new BusinessInformationDto("BusinessName"));
        when(businessInformationServiceMock.getBusinessInformation(anyString())).thenReturn(businessInformationMock);
        mockMvc.perform(get(LOOKUP_URI_PATH)).andExpect(status().isOk());
    }

    private BusinessInformation mockBusinessInformation(boolean isValid, BusinessInformationDto dto) {
        BusinessInformation responseMock = mock(BusinessInformation.class);
        when(responseMock.isValidIdentifier()).thenReturn(isValid);
        when(responseMock.getDto()).thenReturn(dto);
        return responseMock;
    }
}
