FROM maven:3.6-jdk-8 AS builder
WORKDIR /ws/component/

COPY . .

RUN mvn package

FROM openjdk:8-alpine

WORKDIR /ws/component/

COPY --from=builder /ws/component/target/PM-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "/ws/component/PM-0.0.1-SNAPSHOT.jar"]
