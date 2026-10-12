ARG BUILDER_IMAGE=ghcr.io/ministryofjustice/hmpps-hardened-eclipse-temurin-java25:v1.1.1
ARG RUNTIME_IMAGE=ghcr.io/ministryofjustice/hmpps-hardened-distroless-java25:v1.1.2

FROM --platform=$BUILDPLATFORM ${BUILDER_IMAGE} AS builder

ARG BUILD_NUMBER
ENV BUILD_NUMBER=${BUILD_NUMBER:-1_0_0}

WORKDIR /builder
COPY hmpps-user-preferences-${BUILD_NUMBER}.jar app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

FROM ${RUNTIME_IMAGE}
LABEL maintainer="HMPPS Digital Studio <info@digital.justice.gov.uk>"

COPY --chown=2000:2000 applicationinsights.json ./
COPY --chown=2000:2000 applicationinsights.dev.json ./
COPY --chown=2000:2000 applicationinsights-agent*.jar ./agent.jar
COPY --from=builder --chown=2000:2000 /builder/extracted/dependencies/ ./
COPY --from=builder --chown=2000:2000 /builder/extracted/spring-boot-loader/ ./
COPY --from=builder --chown=2000:2000 /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder --chown=2000:2000 /builder/extracted/application/ ./

USER 2000:2000

ENTRYPOINT ["java", "-XX:+ExitOnOutOfMemoryError", "-XX:+AlwaysActAsServerClassMachine", "-javaagent:agent.jar", "-jar", "app.jar"]
