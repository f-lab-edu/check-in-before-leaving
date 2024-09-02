FROM openjdk:17-slim
COPY build/libs/check-in-request-MS-0.0.1-SNAPSHOT.jar check-in-request-MS-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-DSpring.profiles.active=docker","-jar","check-in-request-MS-0.0.1-SNAPSHOT.jar"]