
package com.upiiz.Examen2.Config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {

                    // Reglas de autorización para las API
                    http.requestMatchers(HttpMethod.GET, "/api/v1/listar").hasAnyAuthority("READ");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/crear").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/actualizar/**").hasAuthority("UPDATE");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/eliminar/**").hasAuthority("DELETE");

                    // Denegar acceso a cualquier otra solicitud no configurada
                    http.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails usuario1 = User.withUsername("Miguel")
                .password("1234")
                .roles("ADMIN")
                .authorities("READ", "CREATE", "UPDATE", "DELETE")
                .build();

        UserDetails usuario2 = User.withUsername("Alejandro")
                .password("1234")
                .roles("USER")
                .authorities("READ")
                .build();

        UserDetails usuario3 = User.withUsername("Carlos")
                .password("1234")
                .roles("MODERATOR")
                .authorities("READ", "UPDATE")
                .build();

        UserDetails usuario4 = User.withUsername("Omar")
                .password("1234")
                .roles("EDITOR")
                .authorities("UPDATE")
                .build();

        UserDetails usuario5 = User.withUsername("Axel")
                .password("1234")
                .roles("DEVELOPER")
                .authorities("READ", "CREATE", "UPDATE", "DELETE")
                .build();

        UserDetails usuario6 = User.withUsername("Daniel")
                .password("1234")
                .roles("ANALYST")
                .authorities("READ", "DELETE")
                .build();

        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(usuario1);
        userDetailsList.add(usuario2);
        userDetailsList.add(usuario3);
        userDetailsList.add(usuario4);
        userDetailsList.add(usuario5);
        userDetailsList.add(usuario6);

        return new InMemoryUserDetailsManager(userDetailsList);
    }
}
