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

import java.util.HashSet;
import java.util.Set;

@Component
public class AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();

    @Value("${exambyte.rollen.organisatoren}")
    private Set<String> organisatoren;

    @Value("${exambyte.rollen.korrektoren}")
    private Set<String> korrektoren;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User originalUser = defaultService.loadUser(userRequest);
        Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        if(korrektoren.contains(originalUser.getAttribute("id").toString())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_KORREKTOR"));
        }
        if(organisatoren.contains(originalUser.getAttribute("id").toString())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANISATOR"));
        }
        //TODO Narr1M in yaml Datei
        if("Narr1M".equals(originalUser.getAttribute("login"))) {
            authorities.add(new SimpleGrantedAuthority("ROLE_KORREKTOR"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANISATOR"));
        }
        return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "id");
    }
}
