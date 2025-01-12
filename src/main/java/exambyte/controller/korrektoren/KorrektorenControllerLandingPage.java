package exambyte.controller.korrektoren;

import exambyte.service.korrektoren.AbgabenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class KorrektorenControllerLandingPage {
    private final AbgabenService abgabenService;

    @Autowired
    public KorrektorenControllerLandingPage(AbgabenService abgabenService) {
        this.abgabenService = abgabenService;
    }

    @GetMapping("/korrektoren/landingPage")
    @Secured("ROLE_KORREKTOR")
    public String landingPage(Model model) {
        if(abgabenService.getTestnamen() == null){
            model.addAttribute("testnamen", new ArrayList<>());
        } else {
            model.addAttribute("testnamen", abgabenService.getTestnamen());
            if(abgabenService.getTestnamen().size() == 0){
                model.addAttribute("nichtsZuKorrigieren", true);
            }
        }
        return "korrektoren/LandingPageKorrektoren";
    }

    @GetMapping("/korrektoren/landingPage/zeigeAufgaben/{testname}")
    @Secured("ROLE_KORREKTOR")
    public String aufgaben(Model model, @PathVariable("testname") String testname) {
        model.addAttribute("testname", testname.replace('_', ' '));
        if(abgabenService.getTestnamen() == null){
            model.addAttribute("aufgaben", new ArrayList<>());
        } else {
            model.addAttribute("aufgaben", abgabenService.getAufgabenTitelVonTest(testname.replace('_', ' ')));
        }
        return "korrektoren/AufgabenPageKorrektoren";
    }

    @GetMapping("/korrektoren/landingPage/zeigeAbgaben/{testname}/{aufgabe}")
    @Secured("ROLE_KORREKTOR")
    public String abgaben(Model model, @PathVariable("aufgabe") String aufgabe, @PathVariable("testname") String testname) {
        String aufgabeKorrekt = aufgabe.replace('_',' ');
        String testnameKorrekt = testname.replace('_',' ');
        if(abgabenService.getAbgabenIds(testnameKorrekt, aufgabeKorrekt) == null){
            model.addAttribute("abgaben", new ArrayList<>());
        } else {
            model.addAttribute("abgaben", abgabenService.getAbgabenIds(testnameKorrekt, aufgabeKorrekt));
        }
        return "korrektoren/AbgabenPageKorrektoren";
    }

    @GetMapping("/korrektoren/landingPage/zeigeAbgabe/{abgabeId}")
    @Secured("ROLE_KORREKTOR")
    public String abgabeVonStudi(Model model, @PathVariable("abgabeId") int abgabeId) {
        if(!abgabenService.containsAbgabe(abgabeId)){
            model.addAttribute("abgabeVorhanden", false);
        } else {
            model.addAttribute("abgabeVorhanden", true);
            model.addAttribute("abgabe", abgabenService.getAbgabe(abgabeId));

        }
        return "korrektoren/AbgabeVonStudiPageKorrektoren";
    }

    @PostMapping("/korrektoren/landingPage/zeigeAbgabe/{abgabeId}")
    @Secured("ROLE_KORREKTOR")
    public String korrekturAbschicken(@PathVariable("abgabeId") int abgabeId, @RequestParam(name="feedbackText", defaultValue="") String feedback, @RequestParam(name="punkteVergabe", defaultValue="-1") Integer punkteVergabe) {
        String fehler = "";
        int maxPunkte =abgabenService.getAbgabe(abgabeId).getMaxPunktzahl();

        if(feedback.isEmpty() && !abgabenService.getAbgabe(abgabeId).getStudiantwort().isEmpty()){
            if(punkteVergabe != maxPunkte) {
                fehler += "Das Feedback darf nicht leer sein! ";
            }
        }

        if(punkteVergabe < 0 || punkteVergabe > maxPunkte) {
            fehler += "Die Punktevergabe muss zwischen 0 und " + maxPunkte + " liegen. ";
        }
        if(fehler.isEmpty()) {
            abgabenService.addKorrektur(punkteVergabe, feedback, abgabeId);
            return "redirect:/korrektoren/landingPage/zeigeAufgaben/" + abgabenService.getTestname(abgabeId).replace(" ", "_");
        }
        return "redirect:/korrektoren/landingPage/zeigeAbgabe/{abgabeId}";
    }
}
