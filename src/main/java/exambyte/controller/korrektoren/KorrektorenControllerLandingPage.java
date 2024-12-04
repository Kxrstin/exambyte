package exambyte.controller.korrektoren;

import exambyte.service.korrektoren.AbgabenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class KorrektorenControllerLandingPage {
    private AbgabenService abgabenService;

    @Autowired
    public KorrektorenControllerLandingPage(AbgabenService abgabenService) {
        this.abgabenService = abgabenService;
    }

    @GetMapping("/korrektoren/landingPage")
    @Secured("ROLE_KORREKTOR")
    public String landingPage(Model model) {
        if(abgabenService.getTestnamen() == null){
            model.addAttribute("aufgaben", new ArrayList<>());
        } else {
            model.addAttribute("aufgaben", abgabenService.getTestnamen());
        }
        return "korrektoren/LandingPageKorrektoren";
    }
}
