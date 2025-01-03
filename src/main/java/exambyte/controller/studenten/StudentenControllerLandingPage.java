package exambyte.controller.studenten;

import exambyte.service.studenten.TestFragenService;
import exambyte.aggregates.studenten.StudiTest.StudiTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    public String landingpage(Model model, @AuthenticationPrincipal OAuth2User user) {
        if(testService.getBearbeitbareTests().isEmpty()) {
            model.addAttribute("bearbeitbareTests", new ArrayList<StudiTest>());
        }
         else {
            model.addAttribute("bearbeitbareTests", testService.getBearbeitbareTests());
        }

        if(testService.getAbgelaufeneTests() == null) {
            model.addAttribute("abgelaufeneTests", new ArrayList<StudiTest>());
            model.addAttribute("guterKurs", "Guter Kurs");
        } else {
            String zulassungsstatus = testService.zulassungsStatus(user.getAttribute("id"));
            model.addAttribute("abgelaufeneTests", testService.getAbgelaufeneTests());
            model.addAttribute(zulassungsstatus, true);
        }
        return "studenten/LandingPageStudenten";
    }

    @GetMapping("/studenten/landingPage/zeigeTest/{id}")
    @Secured("ROLE_STUDENT")
    public String zeigeTest(@PathVariable("id") int testId, Model model) {
        if(!testService.hasTestWithId(testId)) {
            return "redirect:/studenten/landingPage";
        }
        model.addAttribute("titel", testService.parseTitel(testId));
        model.addAttribute("startzeitpunkt", testService.parseStart(testId));
        model.addAttribute("endzeitpunkt", testService.parseEnde(testId));
        model.addAttribute("ergebniszeitpunkt", testService.parseErgebnis(testId));
        model.addAttribute("id", testId);
        model.addAttribute("isAbgelaufen", testService.isAbgelaufen(testId));

        return "studenten/TestPageStudenten";
    }
}
