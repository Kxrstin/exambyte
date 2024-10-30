package exambyte.studenten;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentenControllerTestBearbeitenPage.class)
public class StudentenControllerTestBearbeitenPageTest {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Der Pfad /studenten/testBearbeitung führt zur TestBearbeitenPage")
    public void test_testBearbeitenPage() throws Exception {
        mvc.perform(get("/studenten/testBearbeitung"))
                .andExpect(status().isOk())
                .andExpect(view().name("studenten/TestBearbeitenPageStudenten"));
    }
}
