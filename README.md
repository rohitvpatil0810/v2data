# v2data

[![GitHub Repo](https://img.shields.io/badge/GitHub-v2data-blue)](https://github.com/rohitvpatil0810/v2data)

`v2data` is a Spring Boot application that allows users to **upload audio files**, **transcribe them**, and **generate
structured notes**. It features **authentication and authorization**, secure file storage, and email verification.

---

## Core Features

- **Audio Upload** – Upload audio files for transcription.
- **Event-Driven Asynchronous Processing** – Audio transcription and notes generation are executed asynchronously using Spring Boot’s event-driven architecture, ensuring non-blocking request handling.
- **Transcription & Notes Generation** – Uses
  the [v2data-transcriber](https://github.com/rohitvpatil0810/v2data-transcriber) project hosted on Cloudflare Workers,
  leveraging AI models:
    - Speech-to-text: `@cf/openai/whisper-large-v3-turbo`
    - Text Generation: `@cf/meta/llama-3-8b-instruct`
    - [Workers AI Documentation](https://developers.cloudflare.com/workers-ai/)
- **Authentication & Authorization** – Secure authentication using JWT-based access and refresh tokens (endpoints handle
  authentication and registration securely).
- **Email Verification** – Handled entirely by the application; Mailgun is used **only as an SMTP relay** to send
  verification emails.
- **File Storage** – Uses AWS SDK with Cloudflare R2 object storage.
- **Logging** – Configured with Logback in both JSON and human-readable formats.

---

## Tech Stack

- **Backend:** Java 23 (build 23+37-2369), Spring Boot 3.5
- **Database:** PostgreSQL + Flyway
- **Security:** Spring Security + JWT
- **Email:** Spring Mail (Mailgun as SMTP relay)
- **Cloud Storage:** AWS SDK + Cloudflare R2
- **Transcription & Notes:** Cloudflare Workers AI
- **Logging:** Logback + Logstash Encoder
- **Build Tool:** Maven
- **Containerization:** Docker + Docker Compose

---

## Quick Start (Production)

### Prerequisites

- Java 23
- Maven
- Docker & Docker Compose

### Setup

1. **Clone repository**

```bash
git clone https://github.com/rohitvpatil0810/v2data.git
cd v2data
````

2. **Configure environment variables**
   Copy the provided example file and update your values:

```bash
cp .env.example .env
```

3. **Start supporting services**

```bash
docker-compose up -d
```

4. **Build and run the application**

```bash
mvn clean package
java -jar target/v2data-0.0.1-SNAPSHOT.jar
```

Or using Docker:

```bash
docker build -t v2data .
docker run -p 8080:8080 --env-file .env v2data
```

---

## Development Setup (Local)

1. **Clone repository**

```bash
git clone https://github.com/rohitvpatil0810/v2data.git
cd v2data
```

2. **Set environment variables**

```bash
cp .env.example .env
```

Then update values as needed.

3. **Run supporting services**

```bash
docker-compose up -d
```

4. **Run application in IDE (IntelliJ/Eclipse)**

    * Ensure **Java 23 SDK** is selected
    * Enable **environment variables injection** in run configuration
    * Run `V2dataApplication` main class

5. **Hot reload during development**
   Spring Boot DevTools is included for live reload when source files change.

---

## Logging

Logs are written both to the console (human-readable) and JSON files:

* **Console:** Human-readable
* **File:** `logs/app.json` (rotates daily, max 30 days)

**Logback configuration snippet:**

```xml

<configuration scan="true">
    <property name="LOG_PATH" value="logs"/>
    <appender name="JSON_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_PATH}/app.json</file>
        ...
    </appender>
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="JSON_FILE"/>
    </root>
</configuration>
```

---

## API Endpoints

For all endpoints, please refer to
the [Postman collection](https://red-space-801814.postman.co/workspace/v2data~a16897c0-e41f-40d7-82e1-79332b7457fb/collection/19695382-02c11a2d-f19b-4c09-9518-85ba04a1291e?action=share&source=copy-link&creator=19695382)
for testing.

---

## Docker

* **Dockerfile:** Multi-stage build (Maven → Runtime) with optimized JVM
* **docker-compose.yml:** PostgreSQL + pgAdmin, database volume persistence

---

## Dependencies

Key dependencies include:

* `spring-boot-starter-web`, `webflux`, `data-jpa`, `security`, `validation`, `mail`
* `AWS SDK v2` for S3 (Cloudflare R2)
* `logstash-logback-encoder` for JSON logs
* `jjwt` for JWT
* `flyway-core` for DB migrations
* `postgresql` driver
* `mapstruct` for DTO mapping
* `tika-core` for file type detection

For full dependency list, see [`pom.xml`](https://github.com/rohitvpatil0810/v2data/blob/main/pom.xml).

---

## Related Projects

* **Transcriber:** [v2data-transcriber](https://github.com/rohitvpatil0810/v2data-transcriber) – handles transcription
  and AI-generated notes using Cloudflare Workers AI.

---

## Author

**Rohit Patil** – [GitHub](https://github.com/rohitvpatil0810) | [Portfolio](https://rohit-patil.vercel.app/)

---

## Issues

- Raise issues from [GitHub Issues](https://github.com/rohitvpatil0810/v2data/issues/new)
- Or send me message through [my website](https://rohitvpatil.vercel.app/#contact)
