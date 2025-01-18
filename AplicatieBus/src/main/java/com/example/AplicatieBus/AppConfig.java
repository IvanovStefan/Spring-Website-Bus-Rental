/** Clasa pentru gestionarea securitatii autentificarii
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus;

import com.example.AplicatieBus.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;

@Configuration
public class AppConfig {

    private final DataSource dataSource;

    public AppConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Dezactivăm CSRF pentru simplitate
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login", "/css/**", "/js/**", "/api/public/**").permitAll() // Permite acces public la `/register`
                        .anyRequest().authenticated() // Orice alt endpoint necesită autentificare
                )
                .formLogin(form -> form
                        .loginPage("/login") // Setează pagina personalizată de login
                        .defaultSuccessUrl("/", true) // Redirecționează la pagina principală după autentificare
                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Redirecționează după logout
                        .permitAll()); // Activează pagina de login implicită

        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, true FROM user WHERE username = ?"
        );
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT username, 'ROLE_USER' FROM user WHERE username = ?"
        );
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

