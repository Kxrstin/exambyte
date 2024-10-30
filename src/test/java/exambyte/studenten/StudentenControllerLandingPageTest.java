package exambyte.studenten;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static exambyte.studenten.TestMother.testListe;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentenControllerLandingPage.class)
public class StudentenControllerLandingPageTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TestService testService;

    @Test
    @DisplayName("Die Route /landingPage führt zum Öffnen der LandingPageStudenten.html Seite und es gibt einen 200 Status.")
    public void test_landingPage() throws Exception {
        mvc.perform(get("/studenten/landingPage"))
                .andExpect(view().name("studenten/LandingPageStudenten"))
                .andExpect(status().isOk());
          }

    @Test
    @DisplayName("Wenn der Ergebnis-Button auf der Startseite gedrückt wird, wird man weitergeleitet auf die ErgebnisPage")
    public void test_ergebnisPageAnzeigen() throws Exception {
        mvc.perform(get("/studenten/landingPage/zeigeErgebnis"))
                .andExpect(status().isOk())
                .andExpect(view().name("studenten/ErgebnisPageStudenten"));
    }

    @Test
    @DisplayName("Wenn der Test-Button auf der Startseite gedrückt wird, wird man weitergeleitet auf die testPage")
    public void test_testPageAnzeigen() throws Exception {
        mvc.perform(get("/studenten/landingPage/zeigeTest"))
                .andExpect(status().isOk())
                .andExpect(view().name("studenten/TestPageStudenten"));
    }

    @Test
    @DisplayName("Auf der LandingPage soll eine Liste von Tests angezeigt werden")
    public void test_testsAnzeigen() throws Exception {
        when(testService.getTests()).thenReturn(testListe());

        String textHtml = mvc.perform(get("/studenten/landingPage"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(textHtml).contains("Test Dummy");
    }
}
