package com.example.app.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service utility class for generating and validating JWT tokens.
 * Configuration values are sourced from application properties.<br>
 *
 * <strong>Original Source:</strong> Todo App Project
 * @version 1.0.0
 */
@Service
public class JWTUtils {
    /**
     * Logger instance used for logging events, errors, or warnings that occur within the JWTUtils class.
     */
    Logger logger = Logger.getLogger(JWTUtils.class.getName());

    /**
     * The secret key used for signing JWT tokens.
     * This value is populated from application properties via the @Value annotation.
     */
    @Value("${jwt-secret}")
    private String jwtSecret;

    /**
     * The expiration time for JWT tokens, specified in milliseconds.
     * This value is populated from application properties via the @Value annotation.
     */
    @Value("${jwt-expiration-ms}")
    private int jwtExpMS;

    /**
     * Generates an encoded JWT token based on the provided user session details only when the user logs in.
     * <p>
     * The token will be signed using the HS256 algorithm and will have a set
     * expiration time.
     * </p>
     * @param myUserDetails An instance of MyUserDetails containing the user's session information.
     * @return A String representing the authenticated JWT token with the user's details.
     */
    public String generateJwtToken(MyUserDetails myUserDetails){
        return Jwts.builder() //puts it into token structure
                .setSubject(myUserDetails.getUsername()) // just the email address
                .setIssuedAt(new Date()) // sets it with today's date
                .setExpiration(new Date(new Date().getTime() + jwtExpMS)) // adds expiration to today's date
                .signWith(SignatureAlgorithm.HS256, jwtSecret) //header and secret info
                .compact();
    }

    /**
     * Retrieves the username from the provided JWT token.
     * <p>
     * This method is intended to be used for every single request to authenticate the user.
     * </p>
     * @param token The JWT token.
     * @return The username.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validates the provided JWT token.
     * <p>
     * This method is intended to be used for every single request.
     * </p>
     * @param authToken The JWT token to validate.
     * @return A boolean indicating whether the token is valid or not.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser() // so I can handle the token structure
                    .setSigningKey(jwtSecret) // passes the secret key to "unlock" token
                    .parseClaimsJws(authToken); //validates the token
            return true;
        } catch (SecurityException e) {
            logger.log(Level.SEVERE, "Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.log(Level.SEVERE, "Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.log(Level.SEVERE, "JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.log(Level.SEVERE, "JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}

