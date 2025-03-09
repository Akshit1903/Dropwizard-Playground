# Use the official Maven image with JDK 17
FROM maven:3.8.8-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller JDK runtime for the final image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/playground.jar /app/app.jar
RUN chmod +x commands/generate_config_remote.sh

# Expose the Dropwizard application port (default: 8080)
EXPOSE 8080

# Run the Dropwizard application
CMD ["java", "-jar", "/app/app.jar", "server", "/app/config.yml"]
