FROM openjdk:17-slim

RUN apt-get update && apt-get install -y curl netcat && rm -rf /var/lib/apt/lists/*

COPY build/libs/front-server-1.0-SNAPSHOT.jar front-server-1.0-SNAPSHOT.jar

ENTRYPOINT ["java","-DSpring.profiles.active=default","-jar","front-server-1.0-SNAPSHOT.jar"]