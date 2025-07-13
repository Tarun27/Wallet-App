# Stage 1: Build with Gradle and JDK 17
FROM gradle:8.7-jdk17 AS build
WORKDIR /home/app

COPY build.gradle settings.gradle ./
COPY gradle gradle
RUN gradle build --no-daemon || return 0

COPY . .
RUN gradle bootJar --no-daemon

# Stage 2: Run the built JAR using JDK 17
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /home/app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
