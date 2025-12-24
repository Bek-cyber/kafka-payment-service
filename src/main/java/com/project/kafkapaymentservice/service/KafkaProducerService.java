package com.project.kafkapaymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendPaymentEvent(String paymentId, String payload) {
        kafkaTemplate.send("payments", paymentId, payload);

        log.info("Kafka producer: event sent. key={}, payload={}", paymentId, payload);
    }
}
