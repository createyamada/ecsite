package jp.co.mgsystems.yuricollection.gootscatalog.commons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/userCreate").permitAll()
        .requestMatchers("/user/register").permitAll()
        .requestMatchers("/user/saveSuccess").permitAll()
        .requestMatchers("/user/verify").permitAll()
        .requestMatchers("/logout").permitAll()
        .requestMatchers("/login").permitAll()
        .anyRequest().authenticated()
        )
        .formLogin(formLogin -> formLogin.loginPage("/login"))
        .build();
             
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
