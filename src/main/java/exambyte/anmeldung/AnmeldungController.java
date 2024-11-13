package exambyte.anmeldung;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnmeldungController {
    @GetMapping("/")
    public String anmeldung() {
        return "anmeldung/AnmeldungPage";
    }

}
