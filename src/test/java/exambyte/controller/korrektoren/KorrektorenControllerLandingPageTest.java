package exambyte.controller.korrektoren;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.helper.WithMockOAuth2User;
import exambyte.security.MethodSecurityConfig;
import exambyte.security.SecurityConfig;
import exambyte.service.korrektoren.AbgabenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KorrektorenControllerLandingPage.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class KorrektorenControllerLandingPageTest {
    private final List<String> testnamen = List.of("Mathematik für Informatik 2", "Programmierpraktikum 2", "Wissenschaftliches Arbeiten");
    private final List<String> aufgaben = List.of("Mathematik für Informatik 2 Aufgabe", "Programmierpraktikum 2 Aufgabe", "Wissenschaftliches Arbeiten Aufgabe");
    private final List<Integer> id = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private final Abgabe abgabe = new Abgabe(122, "Propra 2 Test 7", "Was ist Onion Architektur?", "Onion Architektur ist...", 3);

    @Autowired
    MockMvc mvc;

    @MockBean
    AbgabenService abgabenService;

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Die Seite /korrektoren/landingPage ist für Korrektoren erreichbar.")
    void test_01() throws Exception {
        when(abgabenService.getTestnamen()).thenReturn(null);
        mvc.perform(get("/korrektoren/landingPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("korrektoren/LandingPageKorrektoren"));
    }

    @Test
    @DisplayName("Die Seite /korrektoren/landingPage ist für Nicht-angemeldete nicht erreichbar.")
    void test_02() throws Exception {
        mvc.perform(get("/korrektoren/landingPage"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Die Seite /korrektoren/landingPage zeigt die richtigen Testnamen an.")
    void test_03() throws Exception {
        when(abgabenService.getTestnamen()).thenReturn(testnamen);
        MvcResult result = mvc.perform(get("/korrektoren/landingPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("korrektoren/LandingPageKorrektoren"))
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).contains(testnamen);
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Die Seite /korrektoren/landingPage/zeigeAufgaben/BeispielTestName zeigt die richtigen Aufgaben an.")
    void test_04() throws Exception {
        when(abgabenService.getTestnamen()).thenReturn(testnamen);
        when(abgabenService.getAufgabenVonTest(any())).thenReturn(aufgaben);
        MvcResult result = mvc.perform(get("/korrektoren/landingPage/zeigeAufgaben/BeispielTestName"))
                .andExpect(status().isOk())
                .andExpect(view().name("korrektoren/AufgabenPageKorrektoren"))
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).contains(aufgaben);
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Die Seite /korrektoren/landingPage/zeigeAbgaben/BeispielTestName/Aufgabe zeigt die richtigen Studi IDs an.")
    void test_05() throws Exception {
        when(abgabenService.getAbgabenMitTestnameAufgabe("BeispielTestName", "Aufgabe")).thenReturn(id);
        MvcResult result = mvc.perform(get("/korrektoren/landingPage/zeigeAbgaben/BeispielTestName/Aufgabe"))
                .andExpect(status().isOk())
                .andExpect(view().name("korrektoren/AbgabenPageKorrektoren"))
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).contains(List.of("1", "2", "3","4", "5", "6", "7", "8", "9"));
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Die Seite /korrektoren/landingPage/zeigeAbgabe/122 zeigt die richtige Abgabe an.")
    void test_06() throws Exception {
        when(abgabenService.containsAbgabe(122)).thenReturn(true);
        when(abgabenService.getAbgabe(122)).thenReturn(abgabe);
        MvcResult result = mvc.perform(get("/korrektoren/landingPage/zeigeAbgabe/122"))
                .andExpect(status().isOk())
                .andExpect(view().name("korrektoren/AbgabeVonStudiPageKorrektoren"))
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).contains("Was ist Onion Architektur?", "Onion Architektur ist...", "Maximale Punktzahl: ", "3");
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Eine vollständige Korrektur wird erfolgreich abgeschickt")
    void test_07() throws Exception {
        when(abgabenService.containsAbgabe(122)).thenReturn(true);
        when(abgabenService.getAbgabe(122)).thenReturn(abgabe);
        when(abgabenService.getTestname(122)).thenReturn("Propra 2 Test 7");
        when(abgabenService.getAufgabe(122)).thenReturn("Was ist Onion Architektur");
        mvc.perform(post("/korrektoren/landingPage/zeigeAbgabe/122")
                        .with(csrf())
                        .param("punkteVergabe", "3")
                        .param("feedbackText", "Die Antwort ist korrekt"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/korrektoren/landingPage/zeigeAbgaben/Propra_2_Test_7/Was_ist_Onion_Architektur"));
        verify(abgabenService).addKorrektur(3, "Die Antwort ist korrekt", 122);
    }
}
