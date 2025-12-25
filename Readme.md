# Kafka Payment Service — Idempotent Event Processing (Spring Boot + Kafka)

## Описание

**Kafka Payment Service** — backend-сервис, демонстрирующий корректную обработку
Kafka-событий в условиях **at-least-once delivery** с достижением
**exactly-once семантики на уровне бизнес-логики**.

Проект сфокусирован на:
- реальном поведении Kafka при сбоях и ретраях,
- идемпотентной обработке событий,
- транзакционности и консистентности данных,
- изоляции инфраструктуры сервиса.

---

## Цель проекта

Проект используется для:
- демонстрации production-подходов к Kafka consumer’ам,
- практики работы с event-driven архитектурой,
- понимания разницы между delivery-гарантиями Kafka и бизнес-гарантиями.

---

## Технологии

- Java 21
- Spring Boot 3.x
- Spring Kafka
- Spring Data JPA (Hibernate 6)
- Apache Kafka
- PostgreSQL 16
- Docker / Docker Compose
- Gradle
- Lombok

---

## Архитектура проекта

```text
com.project.kafkapaymentservice
├── consumer
│   └── PaymentEventConsumer
├── service
│   ├── PaymentProcessingService
│   └── ProcessedEventService
├── domain
│   ├── entity
│   │   └── ProcessedEvent
│   └── repository
│       └── ProcessedEventRepository
├── config
│   └── kafka
│       ├── KafkaProducerConfig
│       └── KafkaConsumerConfig
└── KafkaPaymentServiceApplication
```
---
## Exactly-once семантика (Idempotent Consumer)

Exactly-once семантика достигается на стороне consumer, а не за счёт Kafka.

### Реализовано:

* Каждое событие имеет eventId (используется Kafka key)
* В БД хранится таблица processed_events
* На уровне БД задано уникальное ограничение по eventId
* Перед выполнением бизнес-логики проверяется,
было ли событие уже обработано

### Результат:

* повторно доставленные события безопасно игнорируются
* бизнес-операции выполняются ровно один раз
---
### Работа с транзакциями

* Используется @Transactional
* Проверка идемпотентности и бизнес-логика выполняются в одной транзакции
* При сбое:
  *   транзакция откатывается
  *   offset Kafka не считается обработанным
  *   сообщение будет доставлено повторно

### Результат:

* консистентность данных
* отсутствие частично обработанных событий
---
## Защита от race conditions

Проект защищён от race conditions при параллельной обработке Kafka-сообщений.

### Используемые механизмы:

* Уникальный индекс в БД (event_id)
* Транзакционность
* Проверка идемпотентности на уровне БД

### Результат:

* невозможность повторной обработки одного события
* корректная работа при конкурентных consumer-потоках
---
## База данных

Для сервиса используется отдельный PostgreSQL:
* собственная БД
* отдельный пользователь
* отдельный порт (5433)
* изоляция от других сервисов (bank-api, другой сервис в моем гите).
