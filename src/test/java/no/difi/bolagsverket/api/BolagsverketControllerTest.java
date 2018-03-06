package no.difi.bolagsverket.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BolagsverketController.class)
@AutoConfigureMockMvc
public class BolagsverketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetIdentifier_identifierExists_shouldReturn204() throws Exception {
        mockMvc.perform(get("/identifier/162021005489")).andExpect(status().isNoContent());
    }

}
