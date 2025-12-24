package com.project.kafkapaymentservice.controller;

import com.project.kafkapaymentservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final KafkaProducerService producerService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> publishPayment(@PathVariable("id") String id) {
        producerService.sendPaymentEvent(
                id,
                "Payment created with id: " + id
        );

        return ResponseEntity.accepted().build();
    }
}
