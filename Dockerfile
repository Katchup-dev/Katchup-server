FROM amazoncorretto:17
ARG JAR_FILE=build/libs/katchupserver-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} katchupserver.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=blue","-XX:MaxRAMPercentage=75","/katchupserver.jar"]