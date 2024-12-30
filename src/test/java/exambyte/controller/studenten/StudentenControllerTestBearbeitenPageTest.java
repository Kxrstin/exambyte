package exambyte.controller.studenten;

import exambyte.TestcontainersConfiguration;
import exambyte.security.MethodSecurityConfig;
import exambyte.security.SecurityConfig;
import exambyte.helper.WithMockOAuth2User;
import exambyte.service.studenten.TestFragenService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentenControllerTestBearbeitenPage.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class, TestcontainersConfiguration.class})
@ActiveProfiles("test") // Verhindert, dass die Beispieldaten der Application Klasse geladen werden
public class StudentenControllerTestBearbeitenPageTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TestFragenService testService;

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/0 führt zur TestBearbeitenPage")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_testBearbeitenPage() throws Exception {
        new TestServiceBuilder(testService)
                .withPunktzahl(2)
                .withAufgabe("Aufgabe Bla Bla")
                .isFreitext(false)
                .isMCAufgabe(false)
                .withAnzahlAufgaben(0)
                .withNextAndPrevAufgabe();


        mvc.perform(get("/studenten/testBearbeitung/0/0"))
                .andExpect(status().isOk())
                .andExpect(view().name("/studenten/TestBearbeitenPageStudenten"));
    }

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/0 führt zur TestBearbeitenPage und zeigt die Fragestellung und die Punktzahl der Freitextaufgabe.")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_freitextAufgabeWirdGezeigt() throws Exception {
        new TestServiceBuilder(testService)
                .withPunktzahl(2)
                .withAnzahlAufgaben(1)
                .isFreitext(true)
                .isMCAufgabe(false)
                .withAufgabe("Aufgabe Bla Bla")
                .withNextAndPrevAufgabe();


        MvcResult result = mvc.perform(get("/studenten/testBearbeitung/0/0"))
                .andExpect(status().isOk())
                .andExpect(view().name("/studenten/TestBearbeitenPageStudenten"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Aufgabe Bla Bla", "Punktzahl: 2");
    }

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/0 führt zur TestBearbeitenPage und zeigt die Fragestellung, die Antwortmöglichkeiten und die Punktzahl der MC Aufgabe.")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_mcAufgabeWirdGezeigt() throws Exception {
        new TestServiceBuilder(testService)
                .withPunktzahl(2)
                .withAnzahlAufgaben(1)
                .withAntwortMoeglichkeiten(List.of("Antwort A", "Antwort B", "Antwort C"))
                .isMCAufgabe(true)
                .isFreitext(false)
                .withAufgabe("Aufgabe Bla Bla")
                .withNextAndPrevAufgabe();

        MvcResult result = mvc.perform(get("/studenten/testBearbeitung/0/0"))
                .andExpect(status().isOk())
                .andExpect(view().name("/studenten/TestBearbeitenPageStudenten"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(
                "Antwort A", "Antwort B", "Antwort C", "Aufgabe Bla Bla", "Punktzahl: 2");
    }

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/0 führt für Nicht-Studenten zur Github Anmeldung.")
    void test_testBearbeitenPageNichtStudent() throws Exception {
        MvcResult result = mvc.perform(get("/studenten/testBearbeitung/0/0"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung/0/1 führt zur TestBearbeitenPage zeigt die Weiter und Zurück Buttons.")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_buttonsWerdenRichtigAngezeigt() throws Exception {
        new TestServiceBuilder(testService)
                .withPunktzahl(2)
                .isFreitext(false)
                .isMCAufgabe(false)
                .withAnzahlAufgaben(3)
                .withNextAndPrevAufgabe();

        MvcResult result = mvc.perform(get("/studenten/testBearbeitung/0/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/studenten/TestBearbeitenPageStudenten"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(
                " Weiter ", " Zurück ");
    }

    @Test
    @DisplayName("Wenn man eine Antwort in die Freitextaufgabe eingibt, wird diese gespeichert.")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_antwortWirdGespeichert() throws Exception {
        mvc.perform(post("/studenten/testBearbeitung/0/0")
                        .with(csrf())
                        .param("antwort", "Die Test-Antwort."))
                .andExpect(status().is3xxRedirection());;
    }
}
