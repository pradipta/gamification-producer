package com.pradipta.gamificationproducer.config;

import com.pradipta.gamificationproducer.models.kafka.KafkaUserLevelMessage;
import com.pradipta.gamificationproducer.trial.entities.user.TrialUser;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    @Bean
    public ProducerFactory<String, TrialUser> producerFactoryTrialUser() {
        Map<String, Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory(map);
    }
    @Bean
    public ProducerFactory<String, KafkaUserLevelMessage> producerFactoryUserLevelMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory(map);
    }


    @Bean
    public KafkaTemplate<String, TrialUser> kafkaTemplateTrialUser() {
        return new KafkaTemplate<>(producerFactoryTrialUser());
    }

    @Bean
    public KafkaTemplate<String, KafkaUserLevelMessage> kafkaTemplateUserLevelMessage() {
        return new KafkaTemplate<String, KafkaUserLevelMessage>(producerFactoryUserLevelMessage());
    }
}
