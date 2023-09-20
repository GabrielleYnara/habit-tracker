package com.example.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Responsible to configure security settings for the application.<br>
 * This class contains beans and configuration settings related to authentication and authorization.
 *
 * @version 1.2.0
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    /**
     * Creates and returns a BCryptPasswordEncoder object.
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtRequestFilter authJwtRequestFilter(){
        return new JwtRequestFilter();
    }

    /**
     * Configures and returns a SecurityFilterChain based on the provided HttpSecurity object.
     *
     * @param http HttpSecurity object used for configuration.
     * @return SecurityFilterChain
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //policies on every request:
        http.authorizeRequests().antMatchers("/auth/users", "/auth/users/login", "/auth/users/register").permitAll() //public end-points
                .antMatchers("/h2-console/**").permitAll() //access to database
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //every time will check for jwt token, not session based.
                .and().csrf().disable()
                .headers().frameOptions().disable();
        http.addFilterBefore(authJwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Creates and returns an AuthenticationManager based on the provided AuthenticationConfiguration.
     *
     * @param authConfig AuthenticationConfiguration object.
     * @return AuthenticationManager
     * @throws Exception if an error occurs during creation.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();

    }
}
