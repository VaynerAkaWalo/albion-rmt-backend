FROM eclipse-temurin:21-jre-alpine

WORKDIR /application

COPY extracted/dependencies/ .
COPY extracted/spring-boot-loader/ .
COPY extracted/snapshot-dependencies/ .
COPY extracted/application/ .

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "application.jar"]
