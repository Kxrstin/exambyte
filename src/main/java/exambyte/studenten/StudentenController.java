package exambyte.studenten;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentenController {

    @GetMapping("/studenten/landingPage")
    public String landingpage() {
        return "studenten/LandingPageStudenten";
    }
}
