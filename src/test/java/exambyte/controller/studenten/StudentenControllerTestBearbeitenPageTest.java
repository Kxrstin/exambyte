package exambyte.controller.studenten;

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

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentenControllerTestBearbeitenPage.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class StudentenControllerTestBearbeitenPageTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TestFragenService testService;

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/0 führt zur TestBearbeitenPage")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_testBearbeitenPage() throws Exception {
        when(testService.getPunktzahl(0, 0)).thenReturn(2);
        when(testService.getAufgabe(0, 0)).thenReturn("Aufgabe Bla Bla");
        when(testService.isFreitextAufgabe(0, 0)).thenReturn(false);
        when(testService.isMCAufgabe(0, 0)).thenReturn(false);
        when(testService.getAnzahlAufgaben(0)).thenReturn(0);

        mvc.perform(get("/studenten/testBearbeitung/0/0"))
                .andExpect(status().isOk())
                .andExpect(view().name("/studenten/TestBearbeitenPageStudenten"));
    }

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/0 führt zur TestBearbeitenPage und zeigt die Fragestellung und die Punktzahl der Freitextaufgabe.")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_freitextAufgabeWirdGezeigt() throws Exception {
        when(testService.getPunktzahl(0, 0)).thenReturn(2);
        when(testService.getAufgabe(0, 0)).thenReturn("Aufgabe Bla Bla");
        when(testService.isFreitextAufgabe(0, 0)).thenReturn(true);
        when(testService.isMCAufgabe(0, 0)).thenReturn(false);
        when(testService.getAnzahlAufgaben(0)).thenReturn(1);

        MvcResult result = mvc.perform(get("/studenten/testBearbeitung/0/0"))
                .andExpect(status().isOk())
                .andExpect(view().name("/studenten/TestBearbeitenPageStudenten"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Aufgabe Bla Bla", "Punktzahl: 2");
    }

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/0 führt zur TestBearbeitenPage und zeigt die Fragestellung, die Antwortmöglichkeiten und die Punktzahl der MC Aufgabe.")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_mcAufgabeWirdGezeigt() throws Exception {
        when(testService.getPunktzahl(0, 0)).thenReturn(2);
        when(testService.getAufgabe(0, 0)).thenReturn("Aufgabe Bla Bla");
        when(testService.isFreitextAufgabe(0, 0)).thenReturn(false);
        when(testService.isMCAufgabe(0, 0)).thenReturn(true);
        when(testService.getAnzahlAufgaben(0)).thenReturn(1);
        when(testService.getAntwortMoeglichkeiten(0, 0)).thenReturn(List.of("Antwort A", "Antwort B", "Antwort C"));

        MvcResult result = mvc.perform(get("/studenten/testBearbeitung/0/0"))
                .andExpect(status().isOk())
                .andExpect(view().name("/studenten/TestBearbeitenPageStudenten"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(
                "Antwort A", "Antwort B", "Antwort C", "Aufgabe Bla Bla", "Punktzahl: 2");
    }

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/0 führt für Nicht-Studenten zur Github Anmeldung.")
    public void test_testBearbeitenPageNichtStudent() throws Exception {
        MvcResult result = mvc.perform(get("/studenten/testBearbeitung/0/0"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/1 führt zur TestBearbeitenPage zeigt die Weiter und Zurück Buttons.")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_buttonsWerdenRichtigAngezeigt() throws Exception {
        when(testService.getPunktzahl(0, 1)).thenReturn(2);
        when(testService.isFreitextAufgabe(0, 0)).thenReturn(false);
        when(testService.isMCAufgabe(0, 0)).thenReturn(false);
        when(testService.getAnzahlAufgaben(0)).thenReturn(3);

        MvcResult result = mvc.perform(get("/studenten/testBearbeitung/0/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/studenten/TestBearbeitenPageStudenten"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(
                "Weiter", "Zurück");
    }

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/0 führt zur TestBearbeitenPage zeigt nicht die Weiter und Zurück Buttons.")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_buttonsWerdenNichtAngezeigt() throws Exception {
        when(testService.getPunktzahl(0, 1)).thenReturn(2);
        when(testService.isFreitextAufgabe(0, 0)).thenReturn(false);
        when(testService.isMCAufgabe(0, 0)).thenReturn(false);
        when(testService.getAnzahlAufgaben(0)).thenReturn(1);

        MvcResult result = mvc.perform(get("/studenten/testBearbeitung/0/0"))
                .andExpect(status().isOk())
                .andExpect(view().name("/studenten/TestBearbeitenPageStudenten"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).doesNotContain(
                "Weiter", "Zurück");
    }
}
