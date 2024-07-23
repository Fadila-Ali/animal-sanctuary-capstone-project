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

/**
 * Configuration class for setting up Spring Security.
 *
 * <p>This class configures the security settings for the application, including
 * URL access restrictions, login and logout handling, and password encoding.
 * It uses a custom user details service to handle authentication.
 *
 * <p>Security configuration:
 * <ul>
 *     <li>Allows unrestricted access to registration and index pages.</li>
 *     <li>Restricts access to certain API endpoints based on user roles.</li>
 *     <li>Uses form-based login with a custom login page.</li>
 *     <li>Handles logout requests and ensures that logout is permitted for all users.</li>
 * </ul>
 *
 * @see CustomUserDetailsService
 */

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Provides a bean for password encoding using BCrypt.
     *
     * @return a {@link PasswordEncoder} instance configured with BCrypt
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain with various security settings.
     *
     * <p>This method sets up URL access rules, form login configurations, and logout settings.
     * It also restricts access to specific endpoints based on HTTP methods and user roles.
     *
     * @param http the {@link HttpSecurity} instance used to configure HTTP security
     * @return the configured {@link SecurityFilterChain} instance
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/", "/js/", "/media/").permitAll()
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/users").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/animals/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/animals/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/animals/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/animals/**").hasRole("ADMIN")
                        .requestMatchers("/volunteers/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/volunteers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/volunteers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/volunteers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/visit-slots/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/visit-slots/update-availability/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/visit-slots/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/visits").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/visits/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/animals", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
                );
        return http.build();
    }

    /**
     * Configures global authentication settings.
     *
     * <p>This method sets up the custom user details service and password encoder used for authentication.
     *
     * @param auth the {@link AuthenticationManagerBuilder} instance used to configure authentication
     * @throws Exception if an error occurs during configuration
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
