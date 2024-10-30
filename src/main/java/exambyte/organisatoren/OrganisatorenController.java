package exambyte.organisatoren;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrganisatorenController {
    @GetMapping("/organisatoren/landingPage")
    public String landingPage() {
        return "/organisatoren/LandingPageOrganisatoren";
    }
}
