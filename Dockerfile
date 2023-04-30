FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/katchupserver-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} katchupserver.jar
ENTRYPOINT ["java","-jar","/katchupserver.jar"]