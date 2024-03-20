package com.amir.blog.Configurations;

import com.amir.blog.Security.JwtAuthenticationEntryPoint;
import com.amir.blog.Security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@AllArgsConstructor
@Configuration
public class SecurityConfigure {

    private final JwtAuthenticationEntryPoint point;
    private final JwtAuthenticationFilter filter;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .anyRequest()
                        .permitAll())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    //Database ke liye DAO Authentication provider
    @Bean
    public DaoAuthenticationProvider daoDauAuthenticationProvider()
    {
        DaoAuthenticationProvider dauAuthenticationProvider=new DaoAuthenticationProvider();
        dauAuthenticationProvider.setUserDetailsService(userDetailsService);
        dauAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return dauAuthenticationProvider;
    }

}