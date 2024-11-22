package exambyte.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Value("${exambyte.rollen.organisatoren}")
    private Set<String> organisatoren = new HashSet<>();

    @Value("${exambyte.rollen.korrektoren}")
    private Set<String> korrektoren = new HashSet<>();

    private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User originalUser = defaultService.loadUser(userRequest);
        Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        System.out.println("Durchlauf");
        String id = originalUser.getAttribute("id").toString();
        System.out.println(id);
        System.out.println(korrektoren);
        if (korrektoren.contains(id)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_KORREKTOR"));
        }
        if (organisatoren.contains(id)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANISATOR"));
        }

        return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "id");
    }
}
