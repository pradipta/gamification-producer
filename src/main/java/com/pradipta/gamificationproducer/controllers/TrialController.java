package com.pradipta.gamificationproducer.controllers;

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
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String trialTopic = "trial_topic";

    @Autowired
    public TrialController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @PostMapping
    public ResponseEntity<String> postTrialMessage(@RequestBody String message) {
        kafkaTemplate.send(trialTopic, message);
        return ResponseEntity.ok("Message Sent");
    }
}
