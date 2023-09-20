package com.example.app.model.response;
/**
 * Represents a login response containing a JWT (JSON Web Token) for authentication.<br>
 * This class is primarily used for creating login response objects.
 *
 * @version 1.0.0
 */
public class LoginResponse {
    private String jwt;

    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
