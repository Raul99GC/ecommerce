package com.raulcg.ecommerce.security;

import com.raulcg.ecommerce.security.jwt.AuthEntryPointJwt;
import com.raulcg.ecommerce.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthTokenFilter authenticationJwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(
                managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.cors(Customizer.withDefaults());

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/api/v1/admin/**").authenticated()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/**").authenticated()
                .anyRequest().permitAll()
        );

        http.addFilterBefore(authenticationJwtTokenFilter, BasicAuthenticationFilter.class);
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    // ? Crea un bean del tipo AuthenticationManager para que esté disponible en el contexto de Spring y pueda ser utilizado en otros lugares de tu aplicación.
    // ? Se utiliza para gestionar el proceso de autenticación en la aplicación.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

}
