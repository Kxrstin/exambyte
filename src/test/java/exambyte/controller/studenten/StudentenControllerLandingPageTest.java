package exambyte.controller.studenten;


import exambyte.aggregates.studenten.StudiTest;
import exambyte.security.MethodSecurityConfig;
import exambyte.security.SecurityConfig;
import exambyte.helper.WithMockOAuth2User;
import exambyte.service.studenten.TestFragenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentenControllerLandingPage.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class StudentenControllerLandingPageTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TestFragenService testService;

    @Test
    @DisplayName("Die Route /landingPage führt zum Öffnen der LandingPageStudenten.html Seite und es gibt einen 200 Status, wenn man kein Student ist.")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_landingPageStudent() throws Exception {
        when(testService.getTestList()).thenReturn(null);
        mvc.perform(get("/studenten/landingPage"))
             .andExpect(view().name("studenten/LandingPageStudenten"))
             .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Die Route /landingPage führt zur GitHub Anmeldung und es gibt einen 3XX Status, wenn man kein Student ist.")
    public void test_landingPageKeinStudent() throws Exception {
        MvcResult result = mvc.perform(get("/studenten/landingPage"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertThat(result.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }

    @Test
    @DisplayName("Wenn der Ergebnis-Button auf der Startseite gedrückt wird, wird man weitergeleitet auf die ErgebnisPage, vorausgesetzt man ist Student.")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_ergebnisPageAnzeigen() throws Exception {
        mvc.perform(get("/studenten/landingPage/zeigeErgebnis/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("studenten/ErgebnisPageStudenten"));
    }

    @Test
    @DisplayName("Wenn ein nicht-Student auf die URL /studenten/landingPage/zeigeErgebnis zugreifen will, wird er zu GitHub Anmeldung redirected.")
    public void test_ergebnisPageAnzeigenNichtStudent() throws Exception {
        MvcResult result = mvc.perform(get("/studenten/landingPage/zeigeErgebnis/1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertThat(result.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }

    @Test
    @DisplayName("Auf der LandingPage soll eine Liste von Tests angezeigt werden")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_testsAnzeigen() throws Exception {
        when(testService.getTestList()).thenReturn(List.of(new StudiTest("Mathematik für Informatik 3 Test Woche 5", 3, LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 28), LocalDate.of(2024, 12, 5))));
        when(testService.zulassungsStatus(any())).thenReturn("guterKurs");
        String textHtml = mvc.perform(get("/studenten/landingPage"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(textHtml).contains("Mathematik für Informatik 3");
    }

    @Test
    @DisplayName("Wenn man auf einen Test klickt, soll man zur richtigen TestPage Seite kommen.")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_testBearbeiten() throws Exception {
        when(testService.hasTestWithId(3)).thenReturn(true);
        when(testService.getTest(3)).thenReturn(new StudiTest("Mathematik für Informatik 3 Test Woche 5", 3, LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 28), LocalDate.of(2024, 12, 5)));
        mvc.perform(get("/studenten/landingPage/zeigeTest/3"))
                .andExpect(status().isOk())
                .andExpect(view().name("studenten/TestPageStudenten"));
    }

    @Test
    @DisplayName("Wenn man alle Tests bislang bestanden hat, soll guter Kurs neben Zulassungsstatus stehen")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_zulassungsstatus() throws Exception {
        StudiTest test = new StudiTest("Mathematik für Informatik 3 Test Woche 5", 3, LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 28), LocalDate.of(2024, 12, 5));
        when(testService.getTestList()).thenReturn(List.of(test));
        when(testService.zulassungsStatus(any())).thenReturn("guterKurs");
        MvcResult result = mvc.perform(get("/studenten/landingPage"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Guter Kurs");
    }

    @Test
    @DisplayName("Wenn man 3 Tests nicht bestanden hat, soll ... mehr als 2 Tests nicht bestanden ... ausgegeben werden.")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_dreiTestsNichtBestanden() throws Exception {
        StudiTest test = new StudiTest("Mathematik für Informatik 3 Test Woche 5", 3, LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 28), LocalDate.of(2024, 12, 5));
        when(testService.getTestList()).thenReturn(List.of(test));
        when(testService.zulassungsStatus(any())).thenReturn("fail");
        MvcResult result = mvc.perform(get("/studenten/landingPage"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("mehr als 2 Tests nicht bestanden");
    }

    @Test
    @DisplayName("Wenn man 1 Mal einen Test nicht bestanden hat, soll ... vorsicht ... ausgegeben werden.")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_einMalNichtBestanden() throws Exception {
        StudiTest test = new StudiTest("Mathematik für Informatik 3 Test Woche 5", 3, LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 28), LocalDate.of(2024, 12, 5));
        when(testService.getTestList()).thenReturn(List.of(test));
        when(testService.zulassungsStatus(any())).thenReturn("vorsicht");
        MvcResult result = mvc.perform(get("/studenten/landingPage"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("vorsicht");
    }
}
