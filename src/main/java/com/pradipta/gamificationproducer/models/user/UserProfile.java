package com.pradipta.gamificationproducer.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
    private Long answerCount;
    private Long correctCount;
}
