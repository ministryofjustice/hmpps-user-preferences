ARG BASE_IMAGE=ghcr.io/ministryofjustice/hmpps-eclipse-temurin:25-jre-jammy
FROM --platform=$BUILDPLATFORM ${BASE_IMAGE} AS builder

ARG BUILD_NUMBER
ENV BUILD_NUMBER ${BUILD_NUMBER:-1_0_0}
ENV BUILD_NUMBER=${BUILD_NUMBER:-1_0_0}

WORKDIR /builder
COPY hmpps-user-preferences-${BUILD_NUMBER}.jar app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

FROM ${BASE_IMAGE}
LABEL maintainer="HMPPS Digital Studio <info@digital.justice.gov.uk>"

RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

ENV TZ=Europe/London
RUN ln -snf "/usr/share/zoneinfo/$TZ" /etc/localtime && echo "$TZ" > /etc/timezone

RUN addgroup --gid 2000 --system appgroup && \
    adduser --uid 2000 --system appuser --gid 2000

ARG BUILD_NUMBER
ENV BUILD_NUMBER=${BUILD_NUMBER:-1_0_0}

WORKDIR /app
COPY --chown=appuser:appgroup applicationinsights.json ./
COPY --chown=appuser:appgroup applicationinsights.dev.json ./
COPY --chown=appuser:appgroup applicationinsights-agent*.jar ./agent.jar
COPY --from=builder --chown=appuser:appgroup /builder/libs/hmpps-user-preferences*.jar /app/app.jar
COPY --from=builder --chown=appuser:appgroup /builder/extracted/dependencies/ ./
COPY --from=builder --chown=appuser:appgroup /builder/extracted/spring-boot-loader/ ./
COPY --from=builder --chown=appuser:appgroup /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder --chown=appuser:appgroup /builder/extracted/application/ ./

USER 2000

ENTRYPOINT ["java", "-XX:+ExitOnOutOfMemoryError", "-XX:+AlwaysActAsServerClassMachine", "-javaagent:agent.jar", "-jar", "app.jar"]
