package com.project.kafkapaymentservice.service;

import com.project.kafkapaymentservice.domain.ProcessedEvent;
import com.project.kafkapaymentservice.domain.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessedEventService {
    private final ProcessedEventRepository eventRepository;

    @Transactional
    public boolean markAsProcessedIfNew(String eventId) {
        if (eventRepository.existsByEventId(eventId)) {
            return false;
        }

        eventRepository.save(ProcessedEvent.of(eventId));

        return true;
    }
}
