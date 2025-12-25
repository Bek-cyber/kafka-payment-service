package com.project.kafkapaymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProcessingService {
    private final ProcessedEventService eventService;

    @Transactional
    public void process(String paymentId, String payload) {
        log.info("Processing payment event: paymentId={}, payload={}", paymentId, payload);

        boolean isNew = eventService.markAsProcessedIfNew(paymentId);

        if (!isNew) {
            log.warn("Duplicate event detected, skipping. paymentId={}", paymentId);
            return;
        }

        log.info("Processing payment business logic. paymentId={}, payload={}", paymentId, payload);
    }
}
