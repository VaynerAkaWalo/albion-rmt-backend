FROM ghcr.io/graalvm/graalvm-community:21 as build

WORKDIR /builder
COPY . .

RUN ./gradlew bootJar
RUN mv build/libs/*.jar application.jar

RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted

FROM eclipse-temurin:21-jre-alpine

WORKDIR /application

COPY --from=build /builder/extracted/dependencies/ .
COPY --from=build /builder/extracted/spring-boot-loader/ .
COPY --from=build /builder/extracted/snapshot-dependencies/ .
COPY --from=build /builder/extracted/application/ .

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "application.jar"]
