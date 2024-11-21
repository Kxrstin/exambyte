package exambyte.controller.studenten;

import exambyte.service.studenten.TestService;
import exambyte.aggregates.studenten.StudiTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class StudentenControllerLandingPage {
    TestService testService;

    @Autowired
    public StudentenControllerLandingPage(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/studenten/landingPage")
    @Secured("ROLE_STUDENT")
    public String landingpage(Model model) {
        if(testService.getTestList() == null) {
            model.addAttribute("tests", new ArrayList<StudiTest>());
        } else {
            model.addAttribute("tests", testService.getTestList());
        }
        return "studenten/LandingPageStudenten";
    }

    @GetMapping("/studenten/landingPage/zeigeErgebnis")
    @Secured("ROLE_STUDENT")
    public String zeigeErgebnis() {
        return "studenten/ErgebnisPageStudenten";
    }

    @GetMapping("/studenten/landingPage/zeigeTest/{id}")
    @Secured("ROLE_STUDENT")
    public String zeigeTest(@PathVariable int id, Model model) {
        // TODO Fehlermeldung
        if(!testService.hasTestWithId(id)) {
            return "redirect:/studenten/landingPage";
        }
        StudiTest test = testService.getTest(id);
        model.addAttribute("titel", "Titel: " + test.getTitel());
        model.addAttribute("startzeitpunkt", "Startzeitpunkt: " + test.getStartzeitpunkt().format(DateTimeFormatter.ofPattern("dd.MM.YYYY")));
        model.addAttribute("endzeitpunkt", "Endzeitpunkt: " + test.getEndzeitpunkt().format(DateTimeFormatter.ofPattern("dd.MM.YYYY")));
        model.addAttribute("ergebniszeitpunkt", "Ergebniszeitpunkt: " + test.getErgebniszeitpunkt().format(DateTimeFormatter.ofPattern("dd.MM.YYYY")));
        return "studenten/TestPageStudenten";
    }
}
