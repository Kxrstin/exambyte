package exambyte.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
        chainBuilder.authorizeHttpRequests(
                config -> config.requestMatchers( "/css/*").permitAll()
                        .anyRequest().authenticated())
                .logout(l -> l.logoutSuccessUrl("/").permitAll())
                .oauth2Login(config ->
                        config.userInfoEndpoint(Customizer.withDefaults()));
        return chainBuilder.build();
    }
}
