package exambyte.controller.studenten;

import exambyte.TestcontainersConfiguration;
import exambyte.aggregates.studenten.StudiTest.FreitextAufgabe;
import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.aggregates.studenten.StudiTest.TestAufgabe;
import exambyte.aggregates.studenten.StudiTest.TestDaten;
import exambyte.helper.WithMockOAuth2User;
import exambyte.security.MethodSecurityConfig;
import exambyte.security.SecurityConfig;
import exambyte.service.studenten.TestFragenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.thymeleaf.spring6.expression.Mvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentenControllerErgebnis.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class, TestcontainersConfiguration.class})
@ActiveProfiles("test") // Verhindert, dass die Beispieldaten der Application Klasse geladen werden
public class StudentenControllerErgebnisTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TestFragenService testService;

    @Test
    @DisplayName("Die Route /zeigeErgebnis/0 führt zum Öffnen der ErgebnisPage.html Seite und es gibt einen 200 Status, wenn man Student ist.")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_landingPageStudent() throws Exception {
        when(testService.hasTestWithId(0)).thenReturn(true);
        when(testService.parseErgebnis(0)).thenReturn("02.12.2024");
        MvcResult result = mvc.perform(get("/studenten/landingPage/zeigeErgebnis/0"))
                .andExpect(view().name("studenten/ErgebnisPageStudenten"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .contains("02.12.2024");
    }

    @Test
    @DisplayName("Die Route /zeigeErgebnis/0 führt zur GitHub Anmeldung und es gibt einen 3XX Status, wenn man kein Student ist.")
    void test_landingPageKeinStudent() throws Exception {
        MvcResult result = mvc.perform(get("/studenten/zeigeErgebnis/0"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertThat(result.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }
}
