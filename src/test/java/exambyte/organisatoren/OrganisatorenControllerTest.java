package exambyte.organisatoren;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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

    @Test
    @DisplayName("MC-Frage Seite redirected zu /organisatoren/testErstellen")
    void test_4() throws Exception {
        mvc.perform(post("/organisatoren/testErstellen/addMcFrage"))
                .andExpect(redirectedUrl("/organisatoren/testErstellen"));
    }

    @Test
    @DisplayName("Freitext-Frage Seite redirected zu /organisatoren/testErstellen")
    void test_5() throws Exception {
        mvc.perform(post("/organisatoren/testErstellen/addFreitextFrage"))
                .andExpect(redirectedUrl("/organisatoren/testErstellen"));
    }
}
