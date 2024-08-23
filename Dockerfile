FROM ubuntu:latest
LABEL authors="aslisahin"

ENTRYPOINT ["top", "-b"]

# Use the official OpenJDK 22 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and build.gradle files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Copy the source code
COPY src src

# Make Gradle wrapper executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build --no-daemon

# Copy the built JAR file into the container
COPY build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
