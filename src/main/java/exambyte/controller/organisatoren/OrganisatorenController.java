package exambyte.controller.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.aggregates.organisatoren.TestFrage;
import exambyte.service.organisatoren.TestFormService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrganisatorenController {
    private TestFormService service;

    public OrganisatorenController(TestFormService service) {
        this.service = service;
    }

    @GetMapping("/organisatoren/landingPage")
    @Secured("ROLE_ORGANISATOR")
    public String landingPage(Model model) {
        model.addAttribute("testForms", service.getTestForms());
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
                             @PathVariable int id) {
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
                                int punkte,
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

    @PostMapping("/organisatoren/testErstellen/testFertigstellen/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String testFertigStellen(@PathVariable int id,
                                    @RequestParam(value = "testfragenIds[]", required = false) List<Integer> testfragenIds,
                                    @RequestParam(value = "fragenTitel[]", required = false) List<String> titelListe,
                                    @RequestParam(value = "fragestellungen[]", required = false) List<String> fragestellungen,
                                    @RequestParam(value = "punkte[]", required = false) List<Integer> punkte,
                                    @RequestParam(value = "erklaerungen[]", required = false) List<String> erklaerungen) {
        if(testfragenIds != null) {
            TestFormular testForm = service.getTestFormById(id);
            for(int i = 0; i < testfragenIds.size(); i++) {
                // TODO: Testfragen ist kein Aggregat Root, dementsprechend darf das hier nicht stehen!
                // TODO: Man muss mittels des Aggregat Roots den Test fertigstellen!

            }
        }

        return "redirect:/organisatoren/landingPage";
    }
}
