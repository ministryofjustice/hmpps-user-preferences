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

---

## Branching & Deployments
### Branching
We use the following branching strategy:
- `main` - protected branch, deployable to preprod and prod environments
- `develop` - protected branch, deployable to dev environment on merge
- Feature branches - created from `develop`, merged back to `develop` when ready

We use a Gitflow process, with pull requests from feature branches 
to `develop`, and from `develop` to `main`.

> **Workflow example:**
> 
> 1. Checkout `develop` branch.
> 2. Create a feature branch from `develop`.
> 3. Make changes and commit them on your feature branch.
> 4. Push feature branch to remote.
> 5. Raise a PR from your feature branch to `develop`.
> 6. Ping **#pic-devs** with your PR for review and approval.
> 7. Merge your PR into `develop` (triggers deployment to dev environment)
> 8. Update Jira ticket with pull request link and move to "Ready for QA"
> 9. When ready to release, raise a PR from `develop` to `main` branch.
> 10. Merge PR to `main` (triggers deployment to preprod environment)
> 11. When ready to deploy to prod, approve the deployment in GitHub Actions.


### Deployments

| Env     | Branch  | Trigger   | Approval | Examples                                    |
|---------|---------|-----------|----------|---------------------------------------------|
| dev     | develop | Automatic | None     | When PR is merged to develop branch         |
| qa      | any*    | Manual    | None     | When QA engineneer runs action              |
| preprod | main    | Automatic | None     | When PR is merged to main                   |
| prod    | main    | Automatic | Manual   | When deployment is manually approved in GHA |

**Additional protections:**
1. Commits must be signed
2. Only developers with write access can merge PRs to `develop` and `main` branches.
3. PRs made to `develop` need to be approved by another developer before they can be merged.
4. Only developers with write access can approve deployments to prod.
5. Deployments to qa are triggered manually from the GitHub Actions UI – requires write access.

### QA Deployments

QA deployments are triggered manually from the GitHub Actions UI.
This allows QA to control which branches are deployed to QA, and when.

> **Run the QA deployment:**
> 
> GitHub Actions tab > Deploy to QA (manual) > Run workflow > Select branch to deploy.

A fresh build is created for the selected branch, and deployed to the QA environment.
Helm Chart values for qa environment are in `helm_deploy/values-qa.yaml`.
