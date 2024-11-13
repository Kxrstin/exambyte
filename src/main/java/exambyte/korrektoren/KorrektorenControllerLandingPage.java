package exambyte.korrektoren;

import exambyte.studenten.TestStudenten;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class KorrektorenControllerLandingPage {
    @GetMapping("/korrektoren/landingPage")
    public String landingpage(Model model) {
        return "korrektoren/LandingPageKorrektoren";
    }
}
