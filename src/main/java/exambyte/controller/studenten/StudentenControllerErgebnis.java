package exambyte.controller.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.service.studenten.TestFragenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.format.DateTimeFormatter;

@Controller
public class StudentenControllerErgebnis {
    TestFragenService testService;

    @Autowired
    public StudentenControllerErgebnis(TestFragenService testService) {
        this.testService = testService;
    }

    @GetMapping("/studenten/landingPage/zeigeErgebnis/{id}")
    @Secured("ROLE_STUDENT")
    public String zeigeErgebnis(@PathVariable("id") Integer testId, Model model) {
        if(testService.hasTestWithId(testId)) {
            model.addAttribute("ergebnisZeitpunkt", testService.parseErgebnis(testId));
        } else {
            model.addAttribute("ergebnisZeitpunkt", "Keine Angaben!");
        }
        // TODO Implementierung
        return "studenten/ErgebnisPageStudenten";
    }
}
