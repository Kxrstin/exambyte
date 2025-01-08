package exambyte.controller.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.aggregates.studenten.StudiTest.TestDaten;
import exambyte.helper.WithMockOAuth2User;
import exambyte.security.MethodSecurityConfig;
import exambyte.security.SecurityConfig;
import exambyte.service.studenten.TestFragenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/** **/

@WebMvcTest(StudentenControllerErgebnis.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
@ActiveProfiles("test") // Verhindert, dass die Beispieldaten der Application Klasse geladen werden
public class StudentenControllerErgebnisTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TestFragenService testService;

    private final StudiTest test = new StudiTest(0, new TestDaten("",
            LocalDateTime.of(2024,12,30,10,30),
            LocalDateTime.of(2024,12,30,12,30),
            LocalDateTime.of(2024,12,31,10,30),
            0));

    @Test
    @DisplayName("Die Route /zeigeErgebnis/0 führt zum Öffnen der ErgebnisPage.html Seite " +
            "und es gibt einen 200 Status, wenn man Student ist.")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_ErgebnisPageWirdAngezeigt() throws Exception {
        when(testService.hasTestWithId(0)).thenReturn(true);
        when(testService.getTest(0)).thenReturn(test);
        when(testService.parseErgebnis(0)).thenReturn("31.12.2024 10:30");
        MvcResult result = mvc.perform(get("/studenten/landingPage/zeigeErgebnis/0"))
                .andExpect(view().name("studenten/ErgebnisPageStudenten"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .contains("31.12.2024 10:30");
    }

    @Test
    @DisplayName("Die Route /zeigeErgebnis/0 führt zur GitHub Anmeldung und es gibt einen 3XX Status," +
            "wenn man kein Student ist.")
    void test_ergebnisPageWirdFuerNichtStudentNichtAngezeigt() throws Exception {
        MvcResult result = mvc.perform(get("/studenten/zeigeErgebnis/0"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertThat(result.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }

    @Test
    @DisplayName("Nach Erreichen des Ergebniszeitpunktes wird die Bewertung vom Korrektor angezeigt.")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_ergebnisPageZeigtRichtigesErgebnisAn() throws Exception {
        when(testService.hasTestWithId(0)).thenReturn(true);
        when(testService.getTest(0)).thenReturn(test);
        when(testService.parseErgebnis(0)).thenReturn("31.12.2024 10:30");
        when(testService.getErgebnisInProzent(any(), any())).thenReturn(56.72);
        when(testService.parsePunktzahlFuerErgebnis(any(), any())).thenReturn("28 / 50 Punkte");
        when(testService.testBestanden(any(), any())).thenReturn("Super, Sie haben den Test bestanden!");
        MvcResult result = mvc.perform(get("/studenten/landingPage/zeigeErgebnis/0"))
                .andExpect(view().name("studenten/ErgebnisPageStudenten"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .contains("31.12.2024 10:30",
                        "56.72 %",
                        "28 / 50 Punkte",
                        "Super, Sie haben den Test bestanden!");
    }

    @Test
    @DisplayName("Wenn ein Test nicht vorhanden ist, wird bei allen Daten Keine Angabe! angegeben.")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_wennKeinTestVorhandenDannKeinErgebnisAngezeigt() throws Exception {
        MvcResult result = mvc.perform(get("/studenten/landingPage/zeigeErgebnis/0"))
                .andExpect(view().name("studenten/ErgebnisPageStudenten"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .contains("<span>Ergebniszeitpunkt: Keine Angaben!</span>",
                        "Ergebnis: <span>Keine Angaben!</span>",
                        "Punkte: <span>Keine Angaben!</span>",
                        "<span>Bestanden?: Keine Angaben!</span>");
    }
}
