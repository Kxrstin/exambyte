package exambyte.studenten;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentenController {

    // Methoden für die LandingPage
    @GetMapping("/studenten/landingPage")
    public String landingpage() {
        return "studenten/LandingPageStudenten";
    }

    @GetMapping("/studenten/landingPage/zeigeErgebnis")
    public String zeigeErgebnis() {
        return "studenten/ErgebnisPageStudenten";
    }

    @GetMapping("/studenten/landingPage/zeigeTest")
    public String zeigeTest() {
        return "studenten/TestBearbeitenPageStudenten";
    }


    // Methoden für die ErgebnisPage
}
