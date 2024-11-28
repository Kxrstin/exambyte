package exambyte.controller.studenten;

import exambyte.service.studenten.TestFragenService;
import exambyte.aggregates.studenten.StudiTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class StudentenControllerLandingPage {
    TestFragenService testService;

    @Autowired
    public StudentenControllerLandingPage(TestFragenService testService) {
        this.testService = testService;
    }

    @GetMapping("/studenten/landingPage")
    @Secured("ROLE_STUDENT")
    public String landingpage(Model model) {
        if(testService.getTestList() == null) {
            model.addAttribute("tests", new ArrayList<StudiTest>());
            model.addAttribute("guterKurs", "Guter Kurs");
        } else {
            String zulassungsstatus = testService.zulassungsStatus(testService.getTestList());
            model.addAttribute("tests", testService.getTestList());
            model.addAttribute(zulassungsstatus, true);
        }
        return "studenten/LandingPageStudenten";
    }

    @GetMapping("/studenten/landingPage/zeigeErgebnis/{id}")
    @Secured("ROLE_STUDENT")
    public String zeigeErgebnis(Integer id) {
        // TODO Implementierung
        return "studenten/ErgebnisPageStudenten";
    }

    @GetMapping("/studenten/landingPage/zeigeTest/{id}")
    @Secured("ROLE_STUDENT")
    public String zeigeTest(@PathVariable int id, Model model) {
        // TODO Fehlermeldung und Verbessern
        if(!testService.hasTestWithId(id)) {
            return "redirect:/studenten/landingPage";
        }
        StudiTest test = testService.getTest(id);
        model.addAttribute("titel", testService.parseTitel(test));
        model.addAttribute("startzeitpunkt", testService.parseStart(test));
        model.addAttribute("endzeitpunkt", testService.parseEnde(test));
        model.addAttribute("ergebniszeitpunkt", testService.parseErgebnis(test));
        return "studenten/TestPageStudenten";
    }
}
