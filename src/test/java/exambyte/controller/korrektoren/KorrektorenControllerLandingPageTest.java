package exambyte.controller.korrektoren;

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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(KorrektorenControllerLandingPage.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class KorrektorenControllerLandingPageTest {
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
}
