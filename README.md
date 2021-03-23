# HMPPS User Preferences

[![CircleCI](https://circleci.com/gh/ministryofjustice/hmpps-user-preferences.svg?style=svg)](https://circleci.com/gh/ministryofjustice/hmpps-user-preferences)

An API to store and retrieve an **HMPPS Users**' preferences where an **HMPPS User** is a user authenticated
by [HMPPS Auth](https://github.com/ministryofjustice/hmpps-auth).

A **user preference** is a configuration setting which allows a user to configure their user experience within a
service, or more likely across services.

**Examples of a preference**:

- A default court which a probation court user wants to see court lists for on login
- A display setting such as applying a high contrast style sheet

**Example of what a preference is NOT**:

- A security permission, e.g. a court for which a probation court user has permission to see cases
- A configuration setting required by service specific business logic
- Personally identifiable or sensitive data, e.g. offender identifiers

## Quickstart

### Requirements

- Docker
- Java

Build and test:

```
./gradlew build
```

Run with in-memory database against dev auth:

```
./gradlew bootRun
```

Run with Dockerised Postgres database against dev auth:

```
docker-compose pull
docker-compose up postgres
SPRING_PROFILES_ACTIVE=postgres,dev ./gradlew bootRun
```

## Code Style

[ktlint](https://github.com/pinterest/ktlint) is the authority on style and is enforced on build.

Run `./gradlew ktlintFormat` to fix formatting errors in your code before commit.
