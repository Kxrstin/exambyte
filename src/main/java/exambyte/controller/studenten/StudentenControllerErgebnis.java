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
            if(testService.getTest(testId).getErgebnisZeitpunkt().isAfter(LocalDateTime.now())) {
                model.addAttribute("ergebnisInProzent", testService.getErgebnisInProzent(user.getAttribute("id"), testId) + " %");
                model.addAttribute("punktzahl", testService.parsePunktzahlFuerErgebnis(user.getAttribute("id"), testId));
                model.addAttribute("testBestanden", testService.testBestanden(user.getAttribute("id"), testId));
            }
        } else {
            model.addAttribute("ergebnisZeitpunkt", "Keine Angaben!");
        }
        // TODO Implementierung
        return "studenten/ErgebnisPageStudenten";
    }
}
