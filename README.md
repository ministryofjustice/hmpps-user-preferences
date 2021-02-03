# hmpps-user-preferences

Api to manage court users' preferences

## Quickstart

### Requirements

- Docker
- Java
- Flyway

Build and test:

```
./gradlew build
```

Run:

```
docker-compose pull
docker-compose up -d
```

Migrate database using Flyway

```
flyway migrate -url=jdbc:postgresql://localhost:5432/preferences -user=root -password=dev -locations=filesystem:src/main/resources/db/migration/ -schemas=hmppsuserpreferences
```

Start

```
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

## Code Style

[ktlint](https://github.com/pinterest/ktlint) is the authority on style and is enforced on build.

Run `./gradlew ktlintFormat` to fix formatting errors in your code before commit.
