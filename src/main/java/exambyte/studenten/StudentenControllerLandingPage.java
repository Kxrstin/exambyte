package exambyte.studenten;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentenControllerLandingPage {
    TestService testService;

    @Autowired
    public StudentenControllerLandingPage(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/studenten/landingPage")
    public String landingpage(Model model) {
        if(testService == null) {
            model.addAttribute("tests", new ArrayList<TestStudenten>());
        } else {
            model.addAttribute("tests", testService.getTests());
        }
        return "studenten/LandingPageStudenten";
    }

    @GetMapping("/studenten/landingPage/zeigeErgebnis")
    public String zeigeErgebnis() {
        return "studenten/ErgebnisPageStudenten";
    }

    @GetMapping("/studenten/landingPage/zeigeTest/{id}")
    public String zeigeTest(@PathVariable int id) {
        // TODO Fehlermeldung
        return "studenten/TestPageStudenten";
    }
}
