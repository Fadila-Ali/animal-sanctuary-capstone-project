package com.ali.animalsanctuary.config;

import com.ali.animalsanctuary.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/users").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/animals/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/animals/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/animals/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/animals/**").hasRole("ADMIN")
                        .requestMatchers("/api/volunteers/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/volunteers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/volunteers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/volunteers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/visit-slots/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/visit-slots/update-availability/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/visit-slots/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/visits").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/visits/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/users", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
