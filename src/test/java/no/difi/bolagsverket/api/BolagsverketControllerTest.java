package no.difi.bolagsverket.api;

import no.difi.bolagsverket.service.ValidatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BolagsverketController.class)
@AutoConfigureMockMvc
public class BolagsverketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValidatorService validatorServiceMock;

    @Test
    public void testGetIdentifier_identifierExists_shouldReturn204() throws Exception {
        when(validatorServiceMock.validate(anyString())).thenReturn(true);
        mockMvc.perform(get("/identifier/162021005489")).andExpect(status().isNoContent());
    }

    @Test
    public void testGetIdentifier_identifierDoesNotExist_shouldReturn404() throws Exception {
        when(validatorServiceMock.validate(anyString())).thenReturn(false);
        mockMvc.perform(get("/identifier/162021005489")).andExpect(status().isNotFound());
    }
}
