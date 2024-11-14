package exambyte.anmeldung;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AnmeldungController {
    @GetMapping("/")
    public String anmeldung(OAuth2AuthenticationToken auth, Model model) {
        OAuth2User user = auth.getPrincipal();
        model.addAttribute("login", user.getAttribute("login"));

        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());
        if(authorities.contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            model.addAttribute("isStudent", true);
        }

        if(authorities.contains(new SimpleGrantedAuthority("ROLE_ORGANISATOR"))) {
            model.addAttribute("isOrganisator", true);
        }

        if(authorities.contains(new SimpleGrantedAuthority("ROLE_KORREKTOR"))) {
            model.addAttribute("isKorrektor", true);
        }

        if(model.getAttribute("isStudent") == null && model.getAttribute("isOrganisator") == null && model.getAttribute("isKorrektor") == null) {
            model.addAttribute("noRole", true);
        }
        return "anmeldung/AnmeldungPage";
    }
}
