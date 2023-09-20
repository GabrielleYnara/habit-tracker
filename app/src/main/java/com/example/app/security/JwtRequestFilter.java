package com.example.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    /**
     * Logger instance used for logging events, errors, or warnings that occur within class.
     */
    Logger logger = Logger.getLogger(JwtRequestFilter.class.getName());
    /**
     * Service for handling user-session operations.
     */
    private MyUserDetailsService myUserDetailsService;
    /**
     * Utility for generating and validating JWT tokens.
     */
    private JWTUtils jwtUtils;

    /**
     * Injects dependency with session manager.
     * @param myUserDetailsService The userDetails service
     */
    @Autowired
    public void setMyUserDetailsService(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    /**
     * Injects JWTUtils dependency
     * @param jwtUtils The utility service.
     */
    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Extracts the JWT token from the Authorization header of the given HTTP request.
     * The token should start with "Bearer ".
     * @param request The Http request.
     * @return The extracted JWT token as a String,
     *         or null if no valid token was found.
     */
    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization"); // key
        if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer")){
            return headerAuth.substring(7);
        }
        logger.info("no header");
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwt(request); //Invokes the Jwt parser and parses the request
            if (jwt != null && jwtUtils.validateJwtToken(jwt)){
                String username = jwtUtils.getUserNameFromJwtToken(jwt); //gets email address
                UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username); // gets the user's session information
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //checks credentials
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // this request is valid
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // grants access to the resource
            }
        } catch (Exception e){
            logger.info("cannot set user authentication token");
        }
        filterChain.doFilter(request,response); //submit the request and response
    }
}