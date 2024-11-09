FROM eclipse-temurin:21-jre-alpine

WORKDIR /application

COPY --from=build extracted/dependencies/ .
COPY --from=build extracted/spring-boot-loader/ .
COPY --from=build extracted/snapshot-dependencies/ .
COPY --from=build extracted/application/ .

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "application.jar"]
