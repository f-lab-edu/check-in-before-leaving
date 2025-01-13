FROM openjdk:17-slim
RUN apt-get update && apt-get install -y curl netcat && rm -rf /var/lib/apt/lists/*
COPY build/libs/check-in-request-MS-0.0.1-SNAPSHOT.jar check-in-request-MS-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-DSpring.profiles.active=docker","-jar","check-in-request-MS-0.0.1-SNAPSHOT.jar"]