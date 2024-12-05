package exambyte.controller.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.service.organisatoren.TestFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrganisatorenController {
    private TestFormService service;

    public OrganisatorenController(TestFormService service) {
        this.service = service;
    }

    @GetMapping("/organisatoren/landingPage")
    @Secured("ROLE_ORGANISATOR")
    public String landingPage() {
        return "/organisatoren/LandingPageOrganisatoren";
    }


    @GetMapping("/organisatoren/testErstellen")
    @Secured("ROLE_ORGANISATOR")
    public String testErstellen(Model model) {
        boolean redirect = false;
        int id = 0;

        if(model.getAttribute("redirect") != null) {
            redirect = (boolean) model.getAttribute("redirect");
        }

        if(redirect == false) {
            id = service.addNewTestForm();
            model.addAttribute("id", id);
        } else {
            id = (int) model.getAttribute("id");
        }

        model.addAttribute("testForm", service.getTestFormById(id));
        return "/organisatoren/TestErstellenPageOrganisatoren";
    }

    @PostMapping("/organisatoren/testErstellen/addMcFrage/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String addMcFrage(RedirectAttributes redirectAttributes,
                             Model model,
                             @PathVariable int id) {
        TestFormular testForm = service.getTestFormById(id);
        testForm.addNewMCFrage();
        service.saveTestForm(testForm);
        redirectAttributes.addFlashAttribute("id", id);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }

    //TODO: alle Methoden, die auf update... antworten ersetzen mit einer einzigen update-Methode, die zugehörige IDs
    //      annimmt.

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

    @PostMapping("/organisatoren/testErstellen/testFertigstellen")
    @Secured("ROLE_ORGANISATOR")
    public String testFertigStellen() {
        return "redirect:/organisatoren/landingPage";
    }
}
