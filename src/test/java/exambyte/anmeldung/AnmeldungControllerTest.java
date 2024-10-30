package exambyte.anmeldung;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnmeldungController.class)
public class AnmeldungControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Wenn der Pfad /anmeldung geöffnet wird, soll man zur AnmeldungPage geleitet werden")
    public void test_anmeldenPage() throws Exception {
        mvc.perform(get("/anmeldung"))
                .andExpect(status().isOk())
                .andExpect(view().name("anmeldung/AnmeldungPage"));
    }

    @Test
    @DisplayName("Wenn das Formular ohne Passwort abgeschickt wird, bekommt man eine Fehlermeldung und einen 302 Status")
    public void test_anmeldenDateneingabe() throws Exception {
        mvc.perform(post("/anmeldung")
                .param("mail", "max.mustermann@hhu.de"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/anmeldung"))
                .andReturn()
                .getResponse()
                .getContentAsString().contains("darf nicht leer sein");
    }


}
