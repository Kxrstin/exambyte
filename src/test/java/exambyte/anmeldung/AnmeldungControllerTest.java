package exambyte.anmeldung;

import exambyte.MethodSecurityConfig;
import exambyte.SecurityConfig;
import exambyte.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.thymeleaf.spring6.expression.Mvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

}
