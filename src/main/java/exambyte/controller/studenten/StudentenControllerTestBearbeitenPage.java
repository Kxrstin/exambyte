package exambyte.controller.studenten;

import exambyte.service.studenten.TestFragenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentenControllerTestBearbeitenPage {
    TestFragenService testService;

    @Autowired
    public StudentenControllerTestBearbeitenPage(TestFragenService testService) {
        this.testService = testService;
    }

    @GetMapping("/studenten/testBearbeitung/{id}")
    @Secured("ROLE_STUDENT")
    public String testBearbeitung(Model model, @PathVariable("id") Integer testId) {
        return "redirect:/studenten/testBearbeitung/" + testId + "/0";
    }

    @GetMapping("/studenten/testBearbeitung/{id}/{aufgabe}")
    @Secured("ROLE_STUDENT")
    public String aufgabenBearbeitung(Model model, @PathVariable("id") Integer testId, @PathVariable("aufgabe") Integer aufgabenNr) {
        model.addAttribute("aufgabe", aufgabenNr);
        model.addAttribute("id", testId);
        model.addAttribute("aufgabenstellung", "Aufgabe: " + testService.getAufgabe(testId, aufgabenNr));
        model.addAttribute("punktzahl", "Maximale Punktzahl: " + testService.getPunktzahl(testId, aufgabenNr));

        if(testService.isFreitextAufgabe(testId, aufgabenNr)) {
            model.addAttribute("freitextFrage", true);
        }
        if(testService.isMCAufgabe(testId, aufgabenNr)) {
            model.addAttribute("mcfrage", true);
            model.addAttribute("antworten", testService.getAntwortMoeglichkeiten(testId, aufgabenNr));
        }

        if (aufgabenNr + 1 >= 0 && aufgabenNr + 1 < testService.getAnzahlAufgaben(testId)) {
            model.addAttribute("weiter", true);
            model.addAttribute("nextAufgabe", aufgabenNr+1);
        }
        if (aufgabenNr - 1 >= 0 && aufgabenNr - 1 < testService.getAnzahlAufgaben(testId)) {
            model.addAttribute("zurueck", true);
            model.addAttribute("prevAufgabe", aufgabenNr-1);
        }
        return "/studenten/TestBearbeitenPageStudenten";
    }
}