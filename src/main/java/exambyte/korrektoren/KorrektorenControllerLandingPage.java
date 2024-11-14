package exambyte.korrektoren;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KorrektorenControllerLandingPage {
    @GetMapping("/korrektoren/landingPage")
    public String landingpage(Model model) {
        return "korrektoren/LandingPageKorrektoren";
    }
}
