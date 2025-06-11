FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN ./gradlew bootJar

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/cinema-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
