package com.project.kafkapaymentservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentProcessingService {

    public void process(String paymentId, String payload) {
        log.info("Processing payment event: paymentId={}, payload={}", paymentId, payload);
    }
}
