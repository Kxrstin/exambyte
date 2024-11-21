package exambyte.controller.studenten;

import exambyte.security.MethodSecurityConfig;
import exambyte.security.SecurityConfig;
import exambyte.controller.studenten.StudentenControllerTestBearbeitenPage;
import exambyte.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentenControllerTestBearbeitenPage.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class StudentenControllerTestBearbeitenPageTest {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung führt zur TestBearbeitenPage")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_testBearbeitenPage() throws Exception {
        mvc.perform(get("/studenten/testBearbeitung"))
                .andExpect(status().isOk())
                .andExpect(view().name("studenten/TestBearbeitenPageStudenten"));
    }

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung führt für Nicht-Studenten zur Github Anmeldung.")
    public void test_testBearbeitenPageNichtStudent() throws Exception {
        MvcResult result = mvc.perform(get("/studenten/testBearbeitung"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }
}
