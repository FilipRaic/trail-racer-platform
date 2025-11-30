# TrailRacer Platform

TrailRacer is a microservices-based application built with Spring Boot. It currently contains two services:

- command-service: write-side service producing domain events to Kafka
- query-service: read-side service consuming domain events and serving read models

Infrastructure services run via Docker Compose: PostgreSQL, Kafka (KRaft), and MailDev for email testing.

---

## 🌟 Features

- RESTful APIs for command and query sides
- PostgreSQL for persistence
- Kafka for asynchronous event propagation between services
- MailDev to preview emails during development

---

## 🚀 Technologies

- Java 25
- Spring Boot 4.0.0
- Gradle (Kotlin DSL)
- Docker & Docker Compose

---

## ⚙️ Prerequisites

Install the following:

- Java 25 (Amazon Corretto recommended)
- Docker Desktop (with Docker Compose)
- Make (optional but recommended; available via Git for Windows or Homebrew/apt)

---

## 🧱 Project layout

- command-service: Spring Boot application producing events
- query-service: Spring Boot application consuming events
- shared: shared event classes (published/consumed via Kafka)
- docker/docker-compose.yml: containers for infra and services
- Makefile: convenience commands to build and run everything

---

## 🛠️ Build

You can use either Make or Gradle directly.

Using Make (recommended):

```bash
# Build all projects
make build

# Build Docker images for command-service and query-service using Jib
make images
```

Using Gradle directly:

```bash
./gradlew clean build
../gradlew -p ../command-service clean jibDockerBuild
../gradlew -p ../query-service clean jibDockerBuild
```

The images built locally are referenced by docker-compose as:

- trail-racer-command:latest
- trail-racer-query:latest

---

## 🐳 Run with Docker Compose

Docker compose file is at `docker/docker-compose.yml`.

Using Make:

```bash
# Build images (if not already built) and start everything
make up

# View logs
make logs
make logs-command
make logs-query

# Stop the stack
make down
```

Using Docker Compose directly:

```bash
docker compose -f docker/docker-compose.yml up -d
docker compose -f docker/docker-compose.yml logs -f
docker compose -f docker/docker-compose.yml down
```

### Services and Ports

- PostgreSQL: localhost:5432 (user: postgres / pass: postgres)
- Kafka: localhost:9092
- MailDev UI: http://localhost:1081 (SMTP on 1026 for host; 1025 inside Docker network)
- command-service: http://localhost:8080
- query-service: http://localhost:8081

---

## 🔑 Configuration notes

- Database connection for services is wired via environment variables in docker-compose to use the `postgres` hostname within the Docker network.
- JWT secret is provided via environment variable `APPLICATION_SECURITY_JWT_SECRET_KEY` for both services in docker-compose; adjust as needed.
- Kafka bootstrap servers are available at `kafka:9092` inside the Docker network and `localhost:9092` from the host.

Important: command-service’s Kafka Producer currently uses a hardcoded `localhost:9092` bootstrap server in `KafkaProducerConfig`. When running the service inside Docker, `localhost` refers to the container, not the host Kafka broker. As a result, producing messages from the container may fail. Options:

1) Run command-service on the host (via `./gradlew :command-service:bootRun`) while Kafka runs in Docker; or
2) Update the producer configuration to read `spring.kafka.bootstrap-servers` from properties/environment instead of the hardcoded value, then set it to `kafka:9092` in docker-compose. This change is recommended for full containerized operation.

---

## 📫 MailDev

MailDev starts with the stack. Access the UI at http://localhost:1081 to view sent emails.

---

## 🧹 Useful Make targets

- `make build` – build all projects
- `make images` – build Docker images via Jib
- `make up` – start the full stack (builds images first)
- `make logs` – follow logs for all services
- `make logs-command` / `make logs-query` – follow logs for a specific service
- `make down` – stop the full stack
