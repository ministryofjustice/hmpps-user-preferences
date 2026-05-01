# HMPPS User Preferences

[![Pipeline [test -> build -> deploy]](https://github.com/ministryofjustice/hmpps-user-preferences/actions/workflows/pipeline.yml/badge.svg)](https://github.com/ministryofjustice/hmpps-user-preferences/actions/workflows/pipeline.yml)
[![Swagger API docs (needs VPN)](https://img.shields.io/badge/API_docs_(needs_VPN)-view-85EA2D.svg?logo=swagger)](https://hmpps-user-preferences.hmpps.service.justice.gov.uk/swagger-ui/index.html)

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

For more information, check our [Runbook](https://dsdmoj.atlassian.net/wiki/spaces/NDSS/pages/2548662614/Prepare+a+Case+for+Sentence+RUNBOOK)

## Quickstart

### Requirements

- Docker
- Java 25

Build and test:

```
./gradlew build
```

Run with in-memory database against dev auth:

```
SPRING_PROFILES_ACTIVE=h2,dev ./gradlew bootRun
```

Run with Dockerised Postgres database against dev auth:

```
docker-compose pull
docker-compose up postgres
SPRING_PROFILES_ACTIVE=postgres,dev ./gradlew bootRun
```

### Initialise the database

The database is automatically initialised on application startup with Flyway. 
If you want to run the application locally with an empty database, run: 
```bash
./scripts/db-init.sh
```

## Builds

We use GitHub Actions for CI builds, and GitHub Container Registry for Docker images.
You can also run builds locally to help with testing and debugging.

### How the build works

The project uses Gradle to produce a Spring Boot fat JAR, and Docker to package it into a container image.
In CI, the pipeline runs `./gradlew assemble`, copies the JAR to the project root, and passes `BUILD_NUMBER` as a Docker build arg. The `Dockerfile` expects the JAR at the project root as `hmpps-user-preferences-${BUILD_NUMBER}.jar`.

### Running a local Docker build

To help debug builds, a helper script is provided to replicate the CI build locally:

```bash
./scripts/docker-build-local.sh
```

### Running checks locally

To run the full build with tests and linting (same as CI):

```bash
./gradlew assemble check
```

## Code Style

[ktlint](https://github.com/pinterest/ktlint) is the authority on style and is enforced on build.

Run `./gradlew ktlintFormat` to fix formatting errors in your code before commit.

## QA Deployments

QA deployments are triggered manually from the GitHub Actions UI.
This allows QA to control which branches are deployed to QA, and when.

Run the QA deployment: 
> Actions tab > Deploy to QA (manual) > Run workflow > Select branch to deploy.

QA helm chart values are in `helm_deploy/values-qa.yaml`.
