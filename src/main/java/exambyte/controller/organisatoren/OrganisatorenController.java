package exambyte.controller.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.service.organisatoren.TestFormService;
import exambyte.service.organisatoren.loader.KorrekturenStandLoader;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrganisatorenController {
    private final TestFormService service;
    private final KorrekturenStandLoader korrekturenLoader;

    public OrganisatorenController(TestFormService service, KorrekturenStandLoader korrekturenStandLoader) {
        this.service = service;
        this.korrekturenLoader = korrekturenStandLoader;
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

        //Ohne die if-Abfrage kann es schnell zu Null-Pointer-Exception kommen
        if(model.getAttribute("redirect") != null) {
            redirect = (boolean) model.getAttribute("redirect");
        }

        //Die Error Attribute befinden sich nur im Model, falls sie aufgetreten sind.
        //Um einen Templating-Error zu vermeiden, müssen sie hier rein.
        if(model.getAttribute("fristError") == null) {
            model.addAttribute("fristError", false);
        }
        if(model.getAttribute("leeresFeld") == null) {
            model.addAttribute("leeresFeld", false);
        }
        if(model.getAttribute("keineMcAntwort") == null) {
            model.addAttribute("keineMcAntwort", false);
        }

        //Alles, was ins Model gehört, kommt sofort rein, damit kein Templating-Error entsteht
        int id = 0;
        if(redirect == false) {
            id = service.addNewTestForm();
            model.addAttribute("id", id);
            model.addAttribute("fehlermeldung", false);
        } else {
            id = (int) model.getAttribute("id");
        }

        model.addAttribute("testForm", service.getTestFormById(id));
        return "/organisatoren/TestErstellenPageOrganisatoren";
    }

    @PostMapping("/organisatoren/testErstellen/saveTestTitel/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String saveTestTitel(@PathVariable int id, @RequestParam String testTitel, RedirectAttributes redirectAttributes) {
        if(testTitel.equals("") || testTitel == null) {
            redirectAttributes.addFlashAttribute("fehlermeldung", true);
            redirectAttributes.addFlashAttribute("leeresFeld", true);
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("redirect", true);
            return "redirect:/organisatoren/testErstellen";
        }
        TestFormular testFormular = service.getTestFormById(id);
        testFormular.setTestTitel(testTitel);
        service.save(testFormular);
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
        if(startzeitpunkt == null || endzeitpunkt == null || ergebniszeitpunkt == null) {
            redirectAttributes.addFlashAttribute("fehlermeldung", true);
            redirectAttributes.addFlashAttribute("leeresFeld", true);
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("redirect", true);
            return "redirect:/organisatoren/testErstellen";
        }
        if(startzeitpunkt.isAfter(endzeitpunkt) || endzeitpunkt.isAfter(ergebniszeitpunkt) || ergebniszeitpunkt.isBefore(startzeitpunkt)) {
            redirectAttributes.addFlashAttribute("fehlermeldung", true);
            redirectAttributes.addFlashAttribute("fristError", true);
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("redirect", true);
            return "redirect:/organisatoren/testErstellen";
        }

        TestFormular testFormular = service.getTestFormById(id);

        testFormular.setStartzeitpunkt(startzeitpunkt);
        testFormular.setEndzeitpunkt(endzeitpunkt);
        testFormular.setErgebniszeitpunkt(ergebniszeitpunkt);

        service.save(testFormular);
        redirectAttributes.addFlashAttribute("id", id);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/addMcFrage/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String addMcFrage(RedirectAttributes redirectAttributes, @PathVariable int id) {
        TestFormular testForm = service.getTestFormById(id);
        testForm.addNewMCFrage();
        service.save(testForm);
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
        service.save(testForm);

        redirectAttributes.addFlashAttribute("id", id);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/addMcAntwort/{id}/{frageID}")
    @Secured("ROLE_ORGANISATOR")
    public String addMcAntwort(@PathVariable("id") int formID,
                               @PathVariable int frageID,
                               RedirectAttributes redirectAttributes) {
        TestFormular testFormular = service.getTestFormById(formID);
        testFormular.addNewMcAntwort(frageID);
        service.save(testFormular);

        redirectAttributes.addFlashAttribute("id", formID);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/updateMCFrage/{id}/{frageID}")
    @Secured("ROLE_ORGANISATOR")
    public String updateMCFrage(@PathVariable("id") int formID,
                                @PathVariable int frageID,
                                RedirectAttributes redirectAttributes,
                                @RequestParam(defaultValue = "0") int punkte,
                                String titel,
                                String fragestellung,
                                String erklaerung,
                                @RequestParam(value = "antworten", required = false) List<String> antworten) {
        //Fehlerbehandlung
        if(titel == null || titel.equals("") || fragestellung == null || fragestellung.equals("") || erklaerung == null || erklaerung.equals("")) {
            redirectAttributes.addFlashAttribute("fehlermeldung", true);
            redirectAttributes.addFlashAttribute("leeresFeld", true);
            redirectAttributes.addFlashAttribute("id", formID);
            redirectAttributes.addFlashAttribute("redirect", true);
            return "redirect:/organisatoren/testErstellen";
        }
        if(antworten == null) {
            redirectAttributes.addFlashAttribute("fehlermeldung", true);
            redirectAttributes.addFlashAttribute("keineMcAntwort", true);
            redirectAttributes.addFlashAttribute("id", formID);
            redirectAttributes.addFlashAttribute("redirect", true);
            return "redirect:/organisatoren/testErstellen";
        }
        if(!antworten.contains("on")) {
            redirectAttributes.addFlashAttribute("fehlermeldung", true);
            redirectAttributes.addFlashAttribute("keineMcAntwortRichtig", true);
            redirectAttributes.addFlashAttribute("id", formID);
            redirectAttributes.addFlashAttribute("redirect", true);
            return "redirect:/organisatoren/testErstellen";
        }

        /*
        * Die Liste an Antwortmöglichkeiten einer MC-Frage werden hier richtig zugeordnet, weil Checkboxen,
        * die nicht abgehakt wurden auch nicht abgesendet werden. Checkboxen die abgehakt werden, werden als "on"
        * abgesendet, wenn man sie als String abfragt. Somit kann man dann nach folgendem Prinzip arbeiten:
        * Es wird eine Liste entgegengenommen, welche die Antwortmöglichkeiten und "on"-Strings beinhält. Gibt es
        * vor einem String ein "on", dann ist die Antwortmöglichkeit richtig, gibt es vor einem String einen anderen
        * beliebigen String, ist die Antwortmöglichkeit falsch.
        * Nun wird auch immer in der Liste an Strings, zwischen den Antwortmöglichkeiten ein String namens "space" mit-
        * gesendet, um Bugs zu verhindern. Sollte sich die Schleife bei space befinden, dann ist ein Feld leer.
        */
        List<Boolean> abhakungen = new ArrayList<>();
        List<String> antwortNamen = new ArrayList<>();
        for(int i = 0; i < antworten.size(); i++) {
            if(antworten.get(i).equals("on")) {
                abhakungen.add(true);
            } else {
                antwortNamen.add(antworten.get(i));
            }

            if(abhakungen.size() < antwortNamen.size()) {
                abhakungen.add(false);
            }
        }

        TestFormular testFormular = service.getTestFormById(formID);
        testFormular.addMCFrage(punkte, titel, fragestellung, erklaerung, frageID);
        testFormular.addMcAntworten(abhakungen, antwortNamen, frageID);
        service.save(testFormular);
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
        testFormular.addFreitextFrage(punkte, titel, fragestellung, erklaerung, frageID);
        service.save(testFormular);
        redirectAttributes.addFlashAttribute("id", formID);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testErstellen";
    }

    @PostMapping("/organisatoren/testErstellen/testFertigstellen/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String testFertigStellen(@PathVariable int id) {
        TestFormular testForm = service.getTestFormById(id);
        //Weil die DB immer automatisch eine ID mitgibt:
        testForm.setAllTestFormIdsToNull();
        service.saveTestFormDB(testForm);
        return "redirect:/organisatoren/landingPage";
    }

    @GetMapping("/organisatoren/testVorschau/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String testVorschau(@PathVariable int id, Model model) {
        TestFormular testForm = service.getTestFormByIdDB(id);
        if(testForm.getStartzeitpunkt().isBefore(LocalDateTime.now())) {
            return "redirect:/organisatoren/landingPage";
        }
        model.addAttribute("testForm", testForm);
        return "/organisatoren/TestVorschauOrganisatoren";
    }

    @PostMapping("/organisatoren/testVorschau/saveTestFristen/{id}")
    @Secured("ROLE_ORGANISATOR")
    public String saveTestFristenVorschau(@PathVariable int id,
                                  @RequestParam LocalDateTime startzeitpunkt,
                                  @RequestParam LocalDateTime endzeitpunkt,
                                  @RequestParam LocalDateTime ergebniszeitpunkt,
                                  RedirectAttributes redirectAttributes) {
        TestFormular testFormular = service.getTestFormByIdDB(id);

        testFormular.setStartzeitpunkt(startzeitpunkt);
        testFormular.setEndzeitpunkt(endzeitpunkt);
        testFormular.setErgebniszeitpunkt(ergebniszeitpunkt);

        service.saveTestFormDBWithNewZeitpunkten(testFormular);
        redirectAttributes.addFlashAttribute("id",id);
        redirectAttributes.addFlashAttribute("redirect", true);
        return "redirect:/organisatoren/testVorschau/" + id;
    }

    @GetMapping("/organisatoren/korrekturstand")
    @Secured("ROLE_ORGANISATOR")
    public String korrekturStand(Model model) {
        List<String> testMitAufgaben = new ArrayList<>();
        for(Integer testId: korrekturenLoader.loadTestIdsNichtKorrigiert()) {
            for(String aufgabe: korrekturenLoader.loadTestAufgaben(korrekturenLoader.loadTestName(testId))) {
                testMitAufgaben.add(korrekturenLoader.loadTestName(testId) + ": " + aufgabe);
            }
        }
        model.addAttribute("zuKorrigierendeAufgaben", testMitAufgaben);
        return "/organisatoren/KorrekturstandOrganisatoren";
    }

    @GetMapping("/organisatoren/ergebnisse")
    @Secured("ROLE_ORGANISATOR")
    public String ergebnisUebersicht(Model model) {
        List<String> testErgebnisse = new ArrayList<>();
        for(Integer testId: korrekturenLoader.loadTestIdsKorrigiert()) {
            testErgebnisse.add(korrekturenLoader.loadTestName(testId) + ": " + korrekturenLoader.loadTestErgebnisse(testId) + "% korrekt");
        }
        model.addAttribute("korrigierteTests", testErgebnisse);
        return "/organisatoren/ErgebnisUebersichtOrganisatoren";
    }
}
