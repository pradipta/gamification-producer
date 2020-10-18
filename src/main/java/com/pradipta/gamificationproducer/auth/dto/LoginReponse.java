package com.pradipta.gamificationproducer.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReponse {
    private Boolean success;
    private String message;
}
