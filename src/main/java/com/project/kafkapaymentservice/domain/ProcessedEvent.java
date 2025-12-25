package com.project.kafkapaymentservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "processed_events",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_event_id", columnNames = "event_id")})
public class ProcessedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false, updatable = false)
    private String eventId;

    @Column(name = "processed_at", nullable = false)
    private OffsetDateTime processedAt;

    public static ProcessedEvent of(String eventId) {
        return new ProcessedEvent(
                null,
                eventId,
                OffsetDateTime.now()
        );
    }
}
