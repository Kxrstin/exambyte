package exambyte.controller.anmeldung;

import exambyte.security.MethodSecurityConfig;
import exambyte.security.SecurityConfig;
import exambyte.controller.anmeldung.AnmeldungController;
import exambyte.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnmeldungController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class AnmeldungControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Wenn der Pfad / geöffnet wird, soll man zur GitHub Anmeldung redirected werden")
    public void test_anmeldenPage() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertThat(mvcResult.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }

    @Test
    @DisplayName("Ein User ohne Rolle bekommt nur eine Info Nachricht angezeigt und keinen Button")
    @WithMockOAuth2User
    public void test_noRole() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();
        String html = mvcResult.getResponse().getContentAsString();
        assertThat(html).contains("Nur Korrektoren, Studenten und Organisatoren haben Zugriff auf die Website!");
        assertThat(html.contains("Weiter zu Exambyte für Korrektoren")).isFalse();
        assertThat(html.contains("Weiter zu Exambyte für Studenten")).isFalse();
        assertThat(html.contains("Weiter zu Exambyte für Organisatoren")).isFalse();
    }

    @Test
    @DisplayName("Ein Korrektor bekommt nur den Korrektor Button angezeigt.")
    @WithMockOAuth2User(roles = "KORREKTOR")
    public void test_buttonKorrektor() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();
        String html = mvcResult.getResponse().getContentAsString();
        assertThat(html.contains("Weiter zu Exambyte für Korrektoren")).isTrue();
        assertThat(html.contains("Weiter zu Exambyte für Studenten")).isFalse();
        assertThat(html.contains("Weiter zu Exambyte für Organisatoren")).isFalse();
    }

    @Test
    @DisplayName("Ein Student bekommt nur den Studenten Button angezeigt")
    @WithMockOAuth2User(roles = "STUDENT")
    public void test_buttonStudent() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();
        String html = mvcResult.getResponse().getContentAsString();
        assertThat(html.contains("Weiter zu Exambyte für Korrektoren")).isFalse();
        assertThat(html.contains("Weiter zu Exambyte für Studenten")).isTrue();
        assertThat(html.contains("Weiter zu Exambyte für Organisatoren")).isFalse();
    }

    @Test
    @DisplayName("Ein Organisator bekommt nur den Organisator Button angezeigt.")
    @WithMockOAuth2User(roles = "ORGANISATOR")
    public void test_buttonOrganisator() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();
        String html = mvcResult.getResponse().getContentAsString();
        assertThat(html.contains("Weiter zu Exambyte für Korrektoren")).isFalse();
        assertThat(html.contains("Weiter zu Exambyte für Studenten")).isFalse();
        assertThat(html.contains("Weiter zu Exambyte für Organisatoren")).isTrue();
    }

    @Test
    @DisplayName("Ein User mit allen Rollen bekommt alle drei Buttons angezeigt.")
    @WithMockOAuth2User(roles = {"ORGANISATOR","STUDENT","KORREKTOR"})
    public void test_buttonOrganisatorStudentKorrektor() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();
        String html = mvcResult.getResponse().getContentAsString();
        assertThat(html.contains("Weiter zu Exambyte für Korrektoren")).isTrue();
        assertThat(html.contains("Weiter zu Exambyte für Studenten")).isTrue();
        assertThat(html.contains("Weiter zu Exambyte für Organisatoren")).isTrue();
    }

}
