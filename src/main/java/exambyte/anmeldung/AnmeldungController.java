package exambyte.anmeldung;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnmeldungController {
    @GetMapping("/")
    public String anmeldung(OAuth2AuthenticationToken auth, Model model) {
        OAuth2User user = auth.getPrincipal();
        String login = user.getAttribute("login");
        model.addAttribute("login", login);
        return "anmeldung/AnmeldungPage";
    }

}
