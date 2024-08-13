package jp.co.mgsystems.yuricollection.gootscatalog.commons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .anyRequest()
            .authenticated();
        http.
            formLogin()
            .defaultSuccessUrl("/products", true)
            .permitAll();

        return http.build();       
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
