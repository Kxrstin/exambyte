package exambyte.controller.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.service.organisatoren.TestFormService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class OrganisatorenController {
    private TestFormService service;

    public OrganisatorenController(TestFormService service) {
        this.service = service;
    }

    @GetMapping("/organisatoren/landingPage")
    @Secured("ROLE_ORGANISATOR")
    public String landingPage(Model model) {
        model.addAttribute("testForms", service.getTestFormsDB());
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

    @PostMapping("/organisatoren/testErstellen/saveTestTitel/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String saveTestTitel(@PathVariable int id, @RequestParam String testTitel, RedirectAttributes redirectAttributes) {
        TestFormular testFormular = service.getTestFormById(id);
        testFormular.setTestTitel(testTitel);
        service.saveTestForm(testFormular);
        redirectAttributes.addFlashAttribute("id", id);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/saveTestFristen/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String saveTestFristen(@PathVariable int id,
                                  @RequestParam LocalDateTime startzeitpunkt,
                                  @RequestParam LocalDateTime endzeitpunkt,
                                  @RequestParam LocalDateTime ergebniszeitpunkt,
                                  RedirectAttributes redirectAttributes) {
        TestFormular testFormular = service.getTestFormById(id);

        testFormular.setStartzeitpunkt(startzeitpunkt);
        testFormular.setEndzeitpunkt(endzeitpunkt);
        testFormular.setErgebniszeitpunkt(ergebniszeitpunkt);

        service.saveTestForm(testFormular);
        redirectAttributes.addFlashAttribute("id", id);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/addMcFrage/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String addMcFrage(RedirectAttributes redirectAttributes, @PathVariable int id) {
        TestFormular testForm = service.getTestFormById(id);
        testForm.addNewMCFrage();
        service.saveTestForm(testForm);
        redirectAttributes.addFlashAttribute("id", id);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }



    @PostMapping("/organisatoren/testErstellen/addFreitextFrage/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String addFreitextFrage(RedirectAttributes redirectAttributes,
                                   @PathVariable int id) {
        TestFormular testForm = service.getTestFormById(id);
        testForm.addNewFreitextFrage();
        service.saveTestForm(testForm);
        redirectAttributes.addFlashAttribute("id", id);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }

    //TODO: Update Methode so schreiben, dass sie alles ins richtige TestFormular schreibt
    @PostMapping("/organisatoren/testErstellen/updateMCFrage/{id}/{frageID}")
    @Secured("ROLE_ORGANISATOR")
    public String updateMCFrage(@PathVariable("id") int formID,
                                @PathVariable int frageID,
                                RedirectAttributes redirectAttributes,
                                @RequestParam(defaultValue = "0") int punkte,
                                String titel,
                                String fragestellung,
                                String erklaerung) {
        TestFormular testFormular = service.getTestFormById(formID);
        //TODO: Beschreibung hinzufügen
        testFormular.addMCFrage(punkte, titel, fragestellung, "", erklaerung, frageID);
        service.saveTestForm(testFormular);
        redirectAttributes.addFlashAttribute("id", formID);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/updateFreitextFrage/{id}/{frageID}")
    @Secured("ROLE_ORGANISATOR")
    public String updateFreitextFrage(@PathVariable("id") int formID,
                                      @PathVariable int frageID,
                                      RedirectAttributes redirectAttributes,
                                      int punkte,
                                      String titel,
                                      String fragestellung,
                                      String erklaerung) {
        TestFormular testFormular = service.getTestFormById(formID);
        //TODO: Beschreibung hinzufügen
        testFormular.addFreitextFrage(punkte, titel, fragestellung, "", erklaerung, frageID);
        service.saveTestForm(testFormular);
        redirectAttributes.addFlashAttribute("id", formID);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }

    @GetMapping("/organisatoren/korrekturstand")
    @Secured("ROLE_ORGANISATOR")
    public String korrekturStand() {
        return "/organisatoren/KorrekturstandOrganisatoren";
    }


    //TODO: Überarbeiten, nichtmehr nach allen Parametern fragen usw.
    @PostMapping("/organisatoren/testErstellen/testFertigstellen/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String testFertigStellen(@PathVariable int id) {
        TestFormular testForm = service.getTestFormById(id);
        service.saveTestFormDB(testForm);
        return "redirect:/organisatoren/landingPage";
    }

    @GetMapping("/organisatoren/testVorschau/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String testVorschau(@PathVariable int id, Model model) {
        model.addAttribute("testForm", service.getTestFormByIdDB(id));
        return "/organisatoren/TestVorschauOrganisatoren";
    }
}
