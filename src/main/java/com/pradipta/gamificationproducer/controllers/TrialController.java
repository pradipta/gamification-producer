package com.pradipta.gamificationproducer.controllers;

import com.pradipta.gamificationproducer.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/trial")
public class TrialController {
    private final KafkaTemplate<String, User> kafkaTemplate;
    private static final String trialTopic = "trial-topic";

    @Autowired
    public TrialController(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/json-message")
    public ResponseEntity<String> postTrialMessage(@RequestBody User user) {
        kafkaTemplate.send(trialTopic, user);
        return ResponseEntity.ok("Message Sent");
    }
}
