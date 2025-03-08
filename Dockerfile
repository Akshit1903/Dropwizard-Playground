# Use Eclipse Temurin JDK 17 (compatible with your Maven settings)
FROM eclipse-temurin:17-jdk

# Set working directory inside the container
WORKDIR /app

COPY pom.xml ./pom.xml
COPY src ./src
RUN mvn clean package -DskipTests

# Copy the built JAR file and config
#COPY target/playground.jar /app/app.jar
COPY config/local.yml /app/config.yml

# Expose the Dropwizard application port (default: 8080)
EXPOSE 8080

# Run the Dropwizard application
CMD ["java", "-jar", "./target/playground.jar", "server", "/app/config.yml"]
