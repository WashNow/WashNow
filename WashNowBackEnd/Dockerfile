FROM maven:latest

WORKDIR .

COPY . .

RUN mvn install -DskipTests

CMD mvn spring-boot:run