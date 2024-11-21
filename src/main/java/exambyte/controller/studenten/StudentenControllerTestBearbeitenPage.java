package exambyte.controller.studenten;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentenControllerTestBearbeitenPage {

    @GetMapping("/studenten/testBearbeitung")
    @Secured("ROLE_STUDENT")
    public String testBearbeitung() {
        return "studenten/TestBearbeitenPageStudenten";
    }
}
