package exambyte.security;

import org.springframework.beans.factory.annotation.Value;
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

@Component
public class AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Value("${exambyte.rollen.organisatoren}")
    private Set<String> organisatoren;

    @Value("${exambyte.rollen.korrektoren}")
    private Set<String> korrektoren;

    @Value("${exambyte.rollen.studenten}")
    private Set<String> studenten;

    private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            OAuth2User originalUser = defaultService.loadUser(userRequest);
            Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());
            String login = originalUser.getAttribute("login");
            if(studenten.contains(login)) {
                System.out.println("Added " + login + " as Student.");
                authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
            }
        if(korrektoren.contains(login)) {
            System.out.println("Added " + login + " as Korrektor.");
            authorities.add(new SimpleGrantedAuthority("ROLE_KORREKTOR"));
        }
        if(organisatoren.contains(login)) {
            System.out.println("Added " + login + " as Oranisator.");
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANISATOR"));
        }
        return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "id");
    }
}
