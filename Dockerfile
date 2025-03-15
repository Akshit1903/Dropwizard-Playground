FROM alpine:latest

WORKDIR /app

COPY commands/generate_config_remote.sh /app/generate.sh
COPY config/config.template.yml /app/config.template.yml

RUN chmod +x /app/generate.sh \
    && sed -i 's/\r$//' /app/generate.sh \
    && ls -l /app/ \
    && cat /app/generate.sh

RUN /bin/sh /app/generate.sh
RUN ls -l /app/
RUN cat /app/config.template.yml
RUN cat /app/config.yml

## Use the official Maven image with JDK 17
#FROM maven:3.8.8-eclipse-temurin-17 AS builder
#
## Set working directory
#WORKDIR /app
#
## Copy the project files
#RUN ls /app
#COPY pom.xml .
#COPY src ./src
#
## Build the application
#RUN mvn clean package -DskipTests
#
#
#
## Use a smaller JDK runtime for the final image
#FROM eclipse-temurin:17-jdk
## Set working directory
#WORKDIR /app
#
## Copy the built JAR file from the builder stage
#COPY --from=builder /app/target/playground.jar /app/app.jar

# Expose the Dropwizard application port (default: 8080)
EXPOSE 8080

# Run the Dropwizard application
CMD ["java", "-jar", "/app/app.jar", "server", "/app/config.yml"]
