FROM openjdk:17-slim

RUN apt-get update && apt-get install -y curl netcat && rm -rf /var/lib/apt/lists/*

COPY build/libs/member-context-0.0.1-SNAPSHOT.jar member-context-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-DSpring.profiles.active=docker","-jar","member-context-0.0.1-SNAPSHOT.jar"]