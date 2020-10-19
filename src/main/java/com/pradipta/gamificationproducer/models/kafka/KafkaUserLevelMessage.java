package com.pradipta.gamificationproducer.models.kafka;

import lombok.Data;

@Data
public class KafkaUserLevelMessage {
    private String email;
    private int level;
    private int score;
    private int completion;
}
