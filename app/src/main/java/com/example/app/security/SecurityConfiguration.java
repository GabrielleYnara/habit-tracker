package com.example.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //policies on every request:
        http.authorizeRequests().antMatchers("/auth/users", "/auth/users/login", "/auth/users/register").permitAll() //public end-points
                .antMatchers("/h2-console/**").permitAll() //access to database
                .anyRequest().authenticated()
                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //every time will check for jwt token, not session based.
                .and().csrf().disable()
                .headers().frameOptions().disable();
        return http.build();
    }
}
