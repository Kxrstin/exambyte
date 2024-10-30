package exambyte.anmeldung;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AnmeldungController {
    @GetMapping("/anmeldung")
    public String anmeldung(Model model) {
        if(!model.containsAttribute("anmeldeFormular")) {
            model.addAttribute("anmeldeFormular", new AnmeldeFormular("test@email.com", "passwordTest"));
        }
        return "anmeldung/AnmeldungPage";
    }

    @PostMapping("/anmeldung")
    public String anmeldungDaten(Model model, RedirectAttributes redirectAttributes, @Valid AnmeldeFormular anmeldeFormular, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("erfolgreich", "Dieser Durchlauf war nicht erfolgreich");
            redirectAttributes.addFlashAttribute("anmeldeFormular", anmeldeFormular);
            return "redirect:/anmeldung";
        }
        model.addAttribute("erfolgreich", "Super, dieser Durchlauf war erfolgreich");
        return "anmeldung/AnmeldungPage";
    }
}
