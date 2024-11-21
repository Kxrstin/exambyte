package exambyte.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
        chainBuilder.authorizeRequests(
                config -> config.requestMatchers( "/css/*").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(config ->
                        config.userInfoEndpoint(
                                info -> info.userService(new AppUserService())
                        ));
        return chainBuilder.build();
    }
}
