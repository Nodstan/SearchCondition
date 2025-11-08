# -------- BUILD STAGE --------
FROM gradle:8.10-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# -------- RUN STAGE --------
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8092
ENTRYPOINT ["java", "-jar", "app.jar"]