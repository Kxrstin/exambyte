package exambyte.controller.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.aggregates.organisatoren.TestFrage;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrganisatorenController {

    private Map<Integer, TestFormular> tests = new HashMap<>();

    @ModelAttribute("testForm")
    public TestFormular initTestFormular(){
        return new TestFormular(new ArrayList<>());
    }

    @GetMapping("/organisatoren/landingPage")
    @Secured("ROLE_ORGANISATOR")
    public String landingPage() {
        return "/organisatoren/LandingPageOrganisatoren";
    }


    @GetMapping("/organisatoren/testErstellen")
    @Secured("ROLE_ORGANISATOR")
    public String testErstellen() {
        return "/organisatoren/TestErstellenPageOrganisatoren";
    }

    @PostMapping("/organisatoren/testErstellen/addMcFrage")
    @Secured("ROLE_ORGANISATOR")
    public String addMcFrage(RedirectAttributes redirectAttributes,
                             Model model,
                             @ModelAttribute TestFormular testForm) {
        List<TestFrage> testFragen = testForm.getTestFragen();

        redirectAttributes.addFlashAttribute("mcFrageButton", true);

        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/updateTitelMC")
    @Secured("ROLE_ORGANISATOR")
    public String updateTitelMC() {
        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/updateFragestellungMC")
    @Secured("ROLE_ORGANISATOR")
    public String updateFragestellungMC() {
        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/updatePunkteMC")
    @Secured("ROLE_ORGANISATOR")
    public String updatePunkteMC() {
        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/addFreitextFrage")
    @Secured("ROLE_ORGANISATOR")
    public String addFreitextFrage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("freitextFrageButton", true);

        return "redirect:/organisatoren/testErstellen";
    }

    @GetMapping("/organisatoren/korrekturstand")
    @Secured("ROLE_ORGANISATOR")
    public String korrekturStand() {
        return "/organisatoren/KorrekturstandOrganisatoren";
    }
}
