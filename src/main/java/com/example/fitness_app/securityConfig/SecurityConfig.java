package com.example.fitness_app.securityConfig;


import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetails customUserDetails;
    private final JwtFilterChains jwtFilterChains;

    // PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AUTHENTICATION MANAGER PROVIDER
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetails);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // AuthenticationProvider link both userDetails and password together
    /*
     * Authentication manager bean
     * Required for programmatic authentication (e.g., in /generateToken)
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception {
        // // Disable CSRF (not needed for stateless JWT)
        http.csrf(csrf -> csrf.disable());

        http.cors(cors -> cors.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/registers", "/login").permitAll()

                .requestMatchers("/v3/api-docs/**",
                        "/v3/api-docs.yaml",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/webjars/**").permitAll()

                .anyRequest().authenticated()
        );

        // BASIC AUTHORIZATION WITHOUT JWT
//        http.httpBasic(Customizer.withDefaults());

        // Stateless session (required for JWT)
            http.sessionManagement(sess
                    -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//            http.authenticationProvider(authenticationProvider());

//            http.addFilterBefore((Filter) jwtFilterChains, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtFilterChains, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

}
