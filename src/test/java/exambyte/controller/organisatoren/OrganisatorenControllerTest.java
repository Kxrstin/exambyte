package exambyte.controller.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.helper.WithMockOAuth2User;
import exambyte.service.organisatoren.TestFormService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

    TestFormular mockTestForm = mock(TestFormular.class);
    int testId = 69420;

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
        mockTestFormMethoden();
        mockTestFormServiceMethoden();
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
    @DisplayName("Post-Request auf /addMcFrage Seite redirected zu /organisatoren/testErstellen")
    void test_04() throws Exception {
        mockTestFormMethoden();
        mockTestFormServiceMethoden();

        mvc.perform(post("/organisatoren/testErstellen/addMcFrage/123").with(csrf()))
                .andExpect(redirectedUrl("/organisatoren/testErstellen"));
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Post-Request auf /addFreitextFrage Seite redirected zu /organisatoren/testErstellen")
    void test_05() throws Exception {
        mockTestFormMethoden();
        mockTestFormServiceMethoden();

        mvc.perform(post("/organisatoren/testErstellen/addFreitextFrage/123").with(csrf()))
                .andExpect(redirectedUrl("/organisatoren/testErstellen"));
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Aufruf von /organisatoren/testErstellen/updateTestfrage leitet einen wieder auf Test Erstellen zurück")
    void test_06() throws Exception {
        mvc.perform(post("/organisatoren/testErstellen/updateTestfrage/123").with(csrf()))
                .andExpect(redirectedUrl("/organisatoren/testErstellen"));
    }

    @Test
    @Disabled
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Testfragen lassen sich im Repo speichern und sind nach /testFertigStellen im Repo")
    void test_07() throws Exception {
        mockTestFormMethoden();
        mockTestFormServiceMethoden();

        mvc.perform(get("/organisatoren/testErstellen/"));
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("TestErstellenPage hat im hidden-Field die ID des Tests")
    void test_09() throws Exception {
        mockTestFormMethoden();
        mockTestFormServiceMethoden();
        String mvcResult = mvc.perform(get("/organisatoren/testErstellen"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(mvcResult).contains("" + testId);
    }

    @Test
    @WithMockOAuth2User(login = "JoeSchmoe", roles = "ORGANISATOR")
    @DisplayName("Fertigstellen Button leitet einen auf Startseite weiter")
    void test_10() throws Exception {
        mockTestFormMethoden();
        mockTestFormServiceMethoden();

        mvc.perform(post("/organisatoren/testErstellen/testFertigstellen/123")
                        .with(csrf()))
                .andExpect(redirectedUrl("/organisatoren/landingPage"));
    }

    public void mockTestFormMethoden() {
        when(mockTestForm.getTestFragen()).thenReturn(List.of());
        when(mockTestForm.getId()).thenReturn(testId);
    }

    public void mockTestFormServiceMethoden() {
        when(testFormService.addNewTestForm()).thenReturn(testId);
        when(testFormService.getTestFormById(any())).thenReturn(mockTestForm);
    }


}
