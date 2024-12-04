package exambyte.controller.studenten;

import exambyte.service.studenten.TestFragenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class StudentenControllerTestBearbeitenPage {
    TestFragenService testService;

    @Autowired
    public StudentenControllerTestBearbeitenPage(TestFragenService testService) {
        this.testService = testService;
    }

    @GetMapping("/studenten/testBearbeitung/{id}")
    @Secured("ROLE_STUDENT")
    public String testBearbeitung(@PathVariable("id") Integer testId) {
        return "redirect:/studenten/testBearbeitung/" + testId + "/0"; // Man soll immer zur 1. Aufgabe des Tests geleitet werden.
    }

    @GetMapping("/studenten/testBearbeitung/{id}/{aufgabe}")
    @Secured("ROLE_STUDENT")
    public String aufgabenBearbeitung(Model model, @PathVariable("id") Integer testId, @PathVariable("aufgabe") Integer aufgabenNr) {
        model.addAttribute("isAbgelaufen", testService.isAbgelaufen(testId));
        model.addAttribute("aufgabe", aufgabenNr);
        model.addAttribute("id", testId);
        model.addAttribute("aufgabenstellung", "Aufgabe: " + testService.getAufgabe(testId, aufgabenNr));
        model.addAttribute("punktzahl", "Maximale Punktzahl: " + testService.getPunktzahl(testId, aufgabenNr));

        if (testService.isFreitextAufgabe(testId, aufgabenNr)) {
            model.addAttribute("freitextFrage", true);
            model.addAttribute("studiFAntwort",  testService.getAntwort(testId, aufgabenNr));
        }
        if (testService.isMCAufgabe(testId, aufgabenNr)) {
            model.addAttribute("mcfrage", true);
            model.addAttribute("antworten", testService.getAntwortMoeglichkeiten(testId, aufgabenNr));
            String gespeicherteAntwort = testService.getAntwort(testId, aufgabenNr);

            if (gespeicherteAntwort != null && !gespeicherteAntwort.isEmpty()) {
                List<String> gewaehlteAntworten = Arrays.asList(gespeicherteAntwort.substring(1, gespeicherteAntwort.length()-1).split(", "));
                model.addAttribute("gewaehlteAntworten", gewaehlteAntworten);
            } else {
                model.addAttribute("gewaehlteAntworten", Collections.emptyList());
            }
        }

        if (aufgabenNr + 1 >= 0 && aufgabenNr + 1 < testService.getAnzahlAufgaben(testId)) {
            model.addAttribute("weiter", true);
            model.addAttribute("nextAufgabe", aufgabenNr + 1);
        }
        if (aufgabenNr - 1 >= 0 && aufgabenNr - 1 < testService.getAnzahlAufgaben(testId)) {
            model.addAttribute("zurueck", true);
            model.addAttribute("prevAufgabe", aufgabenNr - 1);
        }
        return "/studenten/TestBearbeitenPageStudenten";
    }

    @PostMapping("/studenten/testBearbeitung/{id}/{aufgabe}")
    @Secured("ROLE_STUDENT")
    public String antwortEingabe(@RequestParam(name="checkboxWahlen", required = false) List<String> wahlen,
                                 @RequestParam(name="studiFAntwort", defaultValue="") String antwortFreitext,
                                 @PathVariable("id") Integer id, @PathVariable("aufgabe") int aufgabe)
    {
        if(antwortFreitext!= null && !antwortFreitext.equals("")) {
            testService.addAntwort(id, aufgabe, antwortFreitext);
        }

        if(wahlen != null && !wahlen.isEmpty()) {
            testService.addAntwort(id, aufgabe, wahlen.toString());
        }
        return "redirect:/studenten/testBearbeitung/"+id+"/"+aufgabe;
    }
}