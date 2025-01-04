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

import java.time.LocalDateTime;

@Controller
public class StudentenControllerErgebnis {
    TestFragenService testService;

    @Autowired
    public StudentenControllerErgebnis(TestFragenService testService) {
        this.testService = testService;
    }

    @GetMapping("/studenten/landingPage/zeigeErgebnis/{id}")
    @Secured("ROLE_STUDENT")
    public String zeigeErgebnis(@PathVariable("id") Integer testId, Model model, @AuthenticationPrincipal OAuth2User user) {
        if(testService.hasTestWithId(testId)) {
            model.addAttribute("ergebnisZeitpunkt", testService.parseErgebnis(testId));
            System.out.println(testService.getTest(testId).getErgebnisZeitpunkt() + " " + testService.getTest(testId).getErgebnisZeitpunkt().isAfter(LocalDateTime.now()));
            if(testService.getTest(testId).getErgebnisZeitpunkt().isBefore(LocalDateTime.now())) {
                model.addAttribute("ergebnisInProzent", testService.getErgebnisInProzent(user.getAttribute("id"), testId) + " %");
                model.addAttribute("punktzahl", testService.parsePunktzahlFuerErgebnis(user.getAttribute("id"), testId));
                model.addAttribute("testBestanden", testService.testBestanden(user.getAttribute("id"), testId));
            } else  {
                model.addAttribute("ergebnisInProzent", "In Bearbeitung.");
                model.addAttribute("punktzahl", "In Bearbeitung");
                model.addAttribute("testBestanden", "In Bearbeitung.");
            }
        } else {
            model.addAttribute("ergebnisZeitpunkt", "Ergebniszeitpunkt: Keine Angaben!");
            model.addAttribute("ergebnisInProzent", "Keine Angaben!");
            model.addAttribute("punktzahl", "Keine Angaben!");
            model.addAttribute("testBestanden", "Bestanden?: Keine Angaben!");
        }
        return "studenten/ErgebnisPageStudenten";
    }
}
