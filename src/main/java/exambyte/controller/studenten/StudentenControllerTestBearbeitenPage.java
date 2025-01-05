package exambyte.controller.studenten;

import exambyte.service.studenten.TestFragenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
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
        return "redirect:/studenten/testBearbeitung/" + testId + "/" + testService.getTest(testId).getFirstAufgabe();
    }

    @GetMapping("/studenten/testBearbeitung/{id}/{aufgabe}")
    @Secured("ROLE_STUDENT")
    public String aufgabenBearbeitung(Model model, @PathVariable("id") Integer testId, @PathVariable("aufgabe") Integer aufgabenId, @AuthenticationPrincipal OAuth2User user) {
        model.addAttribute("isAbgelaufen", testService.isAbgelaufen(testId));
        model.addAttribute("aufgabe", aufgabenId);
        model.addAttribute("id", testId);
        model.addAttribute("aufgabenstellung", "Aufgabe: " + testService.getAufgabe(testId, aufgabenId));
        model.addAttribute("punktzahl", "Maximale Punktzahl: " + testService.getPunktzahl(testId, aufgabenId));

        if (testService.isFreitextAufgabe(testId, aufgabenId)) {
            model.addAttribute("freitextFrage", true);
            model.addAttribute("studiFAntwort", testService.getAntwort(testId, aufgabenId, user.getAttribute("id")));
            if(testService.getErgebnisZeitpunkt(testId).isBefore(LocalDateTime.now())) {
                if(testService.getErreichtePunktzahl(testId, aufgabenId, user.getAttribute("id")) != null) {
                    model.addAttribute("erreichtePunkte", "Erreichte Punktzahl: " + testService.getErreichtePunktzahl(testId, aufgabenId, user.getAttribute("id")) + " Punkte");
                    model.addAttribute("feedback", "Feedback: " + testService.getFeedback(testId, aufgabenId, user.getAttribute("id")));
                }
            }
        }
        if (testService.isMCAufgabe(testId, aufgabenId)) {
            model.addAttribute("mcfrage", true);
            model.addAttribute("antworten", testService.getAntwortMoeglichkeiten(testId, aufgabenId));
            String gespeicherteAntwort = testService.getAntwort(testId, aufgabenId, user.getAttribute("id"));

            if (gespeicherteAntwort != null && !gespeicherteAntwort.isEmpty()) {
                List<String> gewaehlteAntworten = Arrays.asList(gespeicherteAntwort.substring(1, gespeicherteAntwort.length()-1).split(", "));
                model.addAttribute("gewaehlteAntworten", gewaehlteAntworten);
            } else {
                model.addAttribute("gewaehlteAntworten", Collections.emptyList());
            }
        }
        model.addAttribute("nextAufgabe", testService.getTest(testId).getNextAufgabe(aufgabenId));
        model.addAttribute("prevAufgabe", testService.getTest(testId).getPrevAufgabe(aufgabenId));

        return "/studenten/TestBearbeitenPageStudenten";
    }

    @PostMapping("/studenten/testBearbeitung/{id}/{aufgabe}")
    @Secured("ROLE_STUDENT")
    public String antwortEingabe(@RequestParam(name="checkboxWahlen", required = false) List<String> wahlen,
                                 @RequestParam(name="studiFAntwort", defaultValue="") String antwortFreitext,
                                 @PathVariable("id") Integer id, @PathVariable("aufgabe") int aufgabenId,
                                 @AuthenticationPrincipal OAuth2User user)
    {
        if(antwortFreitext != null && !antwortFreitext.equals("")) {
            testService.addAntwort(id, aufgabenId, antwortFreitext, user.getAttribute("id"));
        }

        if(wahlen != null && !wahlen.isEmpty()) {
            testService.addAntwort(id, aufgabenId, wahlen.toString(), user.getAttribute("id"));
        }
        return "redirect:/studenten/testBearbeitung/"+id+"/"+aufgabenId;
    }
}