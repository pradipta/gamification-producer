package com.pradipta.gamificationproducer.auth.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String token) {
        this.accessToken = token;
    }
}
