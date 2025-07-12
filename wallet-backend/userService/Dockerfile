# Stage 1: Build the app using Gradle and JDK 21
FROM gradle:8.7-jdk21 AS build
WORKDIR /home/app

# Copy Gradle build files first (to cache dependencies)
COPY build.gradle settings.gradle ./
COPY gradle gradle
RUN gradle build --no-daemon || return 0

# Copy the full source code and build the JAR
COPY . .
RUN gradle bootJar --no-daemon

# Stage 2: Run the built JAR using JDK 21
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /home/app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
