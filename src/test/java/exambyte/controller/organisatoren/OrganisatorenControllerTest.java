package exambyte.controller.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.controller.organisatoren.OrganisatorenController;
import exambyte.helper.WithMockOAuth2User;
import exambyte.service.organisatoren.TestFormService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganisatorenController.class)
public class OrganisatorenControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TestFormService testFormService;

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Die Organisatoren LandingPage ist unter /organisatoren/landingPage erreichbar")
    void test_01() throws Exception {
        mvc.perform(get("/organisatoren/landingPage"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Test Erstellen Seite ist unter /organisatoren/testErstellen erreichbar")
    void test_02() throws Exception {
        mvc.perform(get("/organisatoren/testErstellen"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Korrekturstand Seite ist unter /organisatoren/korrekturstand erreichbar")
    void test_03() throws Exception {
        mvc.perform(get("/organisatoren/korrekturstand"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("MC-Frage Seite redirected zu /organisatoren/testErstellen")
    void test_04() throws Exception {
        mvc.perform(post("/organisatoren/testErstellen/addMcFrage").with(csrf()))
                .andExpect(redirectedUrl("/organisatoren/testErstellen"));
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Freitext-Frage Seite redirected zu /organisatoren/testErstellen")
    void test_05() throws Exception {
        mvc.perform(post("/organisatoren/testErstellen/addFreitextFrage").with(csrf()))
                .andExpect(redirectedUrl("/organisatoren/testErstellen"));
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Hinzufügen eines Titels bei MC-Frage redirected zu /organisatoren/testErstellen")
    void test_06() throws Exception {
        mvc.perform(post("/organisatoren/testErstellen/updateTitelMC").with(csrf()))
                .andExpect(redirectedUrl("/organisatoren/testErstellen"));
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Hinzufügen einer Fragestellung bei MC-Frage redirected zu /organisatoren/testErstellen")
    void test_07() throws Exception {
        mvc.perform(post("/organisatoren/testErstellen/updateFragestellungMC").with(csrf()))
                .andExpect(redirectedUrl("/organisatoren/testErstellen"));
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Hinzufügen einer Punktzahl bei MC-Frage redirected zu /organisatoren/testErstellen")
    void test_08() throws Exception {
        mvc.perform(post("/organisatoren/testErstellen/updatePunkteMC").with(csrf()))
                .andExpect(redirectedUrl("/organisatoren/testErstellen"));
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Fertigstellen Button leitet einen auf Startseite weiter")
    void test_09() throws Exception {
        mvc.perform(post("/organisatoren/testErstellen/testFertigstellen"))
                .andExpect(redirectedUrl("/organisatoren/landingPage"));
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("TestErstellenPage hat im hidden-Field die ID des Tests")
    void test_10() throws Exception {
        int id = 69420;
        TestFormular testForm = mock(TestFormular.class);
        when(testForm.getTestFragen()).thenReturn(List.of());
        when(testForm.getId()).thenReturn(id);

        when(testFormService.addNewTestForm()).thenReturn(id);
        when(testFormService.getTestFormById(any())).thenReturn(testForm);
        String mvcResult = mvc.perform(get("/organisatoren/testErstellen"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(mvcResult).contains("" + id);
    }
}
