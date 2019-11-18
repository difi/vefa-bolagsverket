package no.difi.bolagsverket.api;

import no.difi.bolagsverket.model.Identifier;
import no.difi.bolagsverket.response.BusinessInformation;
import no.difi.bolagsverket.service.BusinessInformationService;
import no.difi.bolagsverket.service.IdentifierValidatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BolagsverketController.class)
public class BolagsverketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IdentifierValidatorService identifierValidatorServiceMock;
    @MockBean
    private BusinessInformationService businessInformationServiceMock;

    @Test
    public void lookupIdentifier_InvalidIdentifier_ResponseShouldBeBadRequest() throws Exception {
        when(identifierValidatorServiceMock.validate(anyString())).thenReturn(false);
        mockMvc.perform(get("/identifier/Invalid")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void lookupIdentifier_ValidIdentifierButNoInformationFoundInBolagsverket_ResponseShouldBeNotFound() throws Exception {
        when(identifierValidatorServiceMock.validate(anyString())).thenReturn(true);
        when(businessInformationServiceMock.getBusinessInformation(any(Identifier.class))).thenReturn(null);
        mockMvc.perform(get("/identifier/2021005489")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void lookupIdentifier_ValidIdentifierAndInformationFoundInBolagsverket_ResponseShouldBeOk() throws Exception {
        when(identifierValidatorServiceMock.validate(anyString())).thenReturn(true);
        String businessName = "BusinessName";
        when(businessInformationServiceMock.getBusinessInformation(any(Identifier.class))).thenReturn(new BusinessInformation(businessName));
        mockMvc.perform(get("/identifier/5566618020")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(businessName));
    }
}
