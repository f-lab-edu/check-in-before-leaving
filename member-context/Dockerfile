FROM openjdk:17-slim
COPY build/libs/member-context-0.0.1-SNAPSHOT.jar member-context-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-DSpring.profiles.active=docker","-jar","member-context-0.0.1-SNAPSHOT.jar"]