package com.example.fitness_app.securityConfig;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilterChains extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

         String authHeader = request.getHeader("Authorization");
         String token = null;
         String userEmail = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
            userEmail = jwtService.getEmailFromToken(token);
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // CHECK TOKEN EXPIRATION
            // 1. DATABASE SE USER LOAD KIYA
//            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userEmail);
//
//            // 2. YAHAN AAPKA isTokenValid USE HOGA
//            if (jwtService.isTokenValid(token, userDetails)) {
//
//                // 3. Authorities ab direct userDetails se aayengi (DB se synchronized)
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );



            if (!jwtService.isTokenExpired(token)) {
                List<String> roles = jwtService.getRolesFromToken(token);
                List<SimpleGrantedAuthority> authorities;
                if (roles != null && !roles.isEmpty()) {
                    authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();
                } else {
                    authorities = Collections.emptyList();
                }

                // USER PASSWORD USE KARKE CHECK KARNA AUR
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userEmail, null, authorities);


                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((request)));

                SecurityContextHolder.getContext().setAuthentication(authentication);


            }

        }

        filterChain.doFilter(request, response);
    }
}
