package exambyte.studenten;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentenControllerTestBearbeitenPage {

    @GetMapping("/studenten/testBearbeitung")
    public String testBearbeitung() {
        return "studenten/TestBearbeitenPageStudenten";
    }
}
