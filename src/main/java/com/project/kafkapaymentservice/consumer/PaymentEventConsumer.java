package com.project.kafkapaymentservice.consumer;

import com.project.kafkapaymentservice.service.PaymentProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {
    private final PaymentProcessingService processingService;

    @KafkaListener(
            topics = "payments",
            groupId = "payment-processor-group"
    )
    public void consumePaymentEvent(
            String message,
            @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {
        log.info("Kafka consumer: received event. key={}, message={}", key, message);

        processingService.process(key, message);
    }
}
