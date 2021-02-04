# hmpps-user-preferences

Api to manage court users' preferences

## Quickstart

### Requirements

- Docker
- Java

Build and test:

```
./gradlew build
```

Run:

```
docker-compose pull
docker-compose up -d
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

## Code Style

[ktlint](https://github.com/pinterest/ktlint) is the authority on style and is enforced on build.

Run `./gradlew ktlintFormat` to fix formatting errors in your code before commit.
