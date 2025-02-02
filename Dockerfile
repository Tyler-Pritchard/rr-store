# Use an official OpenJDK runtime as a parent image for building
FROM eclipse-temurin:17-jdk-alpine AS build

# Set working directory
WORKDIR /app

# Install curl (for testing during the build stage if needed)
RUN apk add --no-cache curl

# Copy Maven project files to leverage caching of dependencies
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Install Maven dependencies
RUN ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Use a lightweight JRE for running the application
FROM eclipse-temurin:17-jre-alpine

# Install curl in the final runtime container (for debugging and health checks)
RUN apk add --no-cache curl

# Set working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/rr-store-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
