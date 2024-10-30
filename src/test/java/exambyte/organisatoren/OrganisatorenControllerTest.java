package exambyte.organisatoren;

import exambyte.organisatoren.OrganisatorenController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganisatorenController.class)
public class OrganisatorenControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Die Organisatoren LandingPage ist unter /organisatoren/landingPage erreichbar")
    void test_1() throws Exception {
        mvc.perform(get("/organisatoren/landingPage"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Erstellen Seite ist unter /organisatoren/testErstellen erreichbar")
    void test_2() throws Exception {
        mvc.perform(get("/organisatoren/testErstellen"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Korrekturstand Seite ist unter /organisatoren/korrekturstand erreichbar")
    void test_3() throws Exception {
        mvc.perform(get("/organisatoren/korrekturstand"))
                .andExpect(status().isOk());
    }


}
