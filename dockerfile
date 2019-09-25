FROM maven:3.6-jdk-8
WORKDIR /ws/JavaTestGeneration/
COPY . .
RUN mvn package
ENTRYPOINT ["bash"]
