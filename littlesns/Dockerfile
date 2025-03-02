FROM openjdk:17-slim

RUN apt-get update && apt-get install -y curl netcat && rm -rf /var/lib/apt/lists/*

COPY build/libs/little-SNS-MS-1.0-SNAPSHOT.jar little-SNS-MS-1.0-SNAPSHOT.jar

ENTRYPOINT ["java","-DSpring.profiles.active=docker","-jar","little-SNS-MS-1.0-SNAPSHOT.jar"]