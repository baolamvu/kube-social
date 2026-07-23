# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Cache dependencies first: this layer is only rebuilt when pom.xml changes.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Compile & package the application.
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre-alpine

# Run as a dedicated, unprivileged user (never root in production).
RUN addgroup -S springgroup && adduser -S -G springgroup springuser

WORKDIR /app

# Copy only the executable jar from the build stage, owned by the non-root user.
COPY --from=build --chown=springuser:springgroup /app/target/*.jar app.jar

USER springuser

EXPOSE 8080

# Let the JVM size its heap from the container's cgroup memory limit.
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError"

# Liveness probe against the (public) actuator health endpoint.
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1

# Exec form so the JVM is PID 1 and receives SIGTERM for graceful shutdown.
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
