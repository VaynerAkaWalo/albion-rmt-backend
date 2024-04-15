FROM azul/zulu-openjdk:21
LABEL authors="vayner"

COPY build/libs/albion-rmt-backend-*.jar /opt/albion-rmt-backend.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=test", "-jar", "/opt/albion-rmt-backend.jar"]