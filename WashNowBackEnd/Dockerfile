FROM openjdk:17-jdk-slim as builder

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests && ls -l /app/target

FROM openjdk:17

COPY --from=builder /app/target/WashNowBackEnd-0.0.1-SNAPSHOT.jar /app/WashNowBackEnd.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/WashNowBackEnd.jar"]