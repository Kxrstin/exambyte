package exambyte.organisatoren;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrganisatorenController {

    @GetMapping("/organisatoren/landingPage")
    public String landingPage() {
        return "/organisatoren/LandingPageOrganisatoren";
    }


    @GetMapping("/organisatoren/testErstellen")
    public String testErstellen() {
        return "/organisatoren/TestErstellenPageOrganisatoren";
    }

    @PostMapping("/organisatoren/testErstellen/addMcFrage")
    public String addMcFrage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mcFrageButton", true);

        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/addFreitextFrage")
    public String addFreitextFrage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("freitextFrageButton", true);

        return "redirect:/organisatoren/testErstellen";
    }

    @GetMapping("/organisatoren/korrekturstand")
    public String korrekturStand() {

        return "/organisatoren/KorrekturstandOrganisatoren";
    }
}
