package com.pradipta.gamificationproducer.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponse {
    private Boolean success;
    private String message;
}
