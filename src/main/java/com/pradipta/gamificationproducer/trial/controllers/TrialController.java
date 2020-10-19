package com.pradipta.gamificationproducer.trial.controllers;

import com.pradipta.gamificationproducer.trial.entities.user.TrialUser;
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
    private final KafkaTemplate<String, TrialUser> kafkaTemplateTrialUser;
    private static final String trialTopic = "trial-topic";

    @Autowired
    public TrialController(KafkaTemplate<String, TrialUser> kafkaTemplateTrialUser) {
        this.kafkaTemplateTrialUser = kafkaTemplateTrialUser;
    }

    @PostMapping("/json-message")
    public ResponseEntity<String> postTrialMessage(@RequestBody TrialUser trialUser) {
        kafkaTemplateTrialUser.send(trialTopic, trialUser);
        return ResponseEntity.ok("Message Sent");
    }
}
