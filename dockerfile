FROM maven:3.6-jdk-8
WORKDIR /ws/component/
COPY . .
RUN mvn package
ENTRYPOINT ["java", "-jar", "/ws/component/target/PM-0.0.1-SNAPSHOT.jar"]
