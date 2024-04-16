FROM azul/zulu-openjdk:21
LABEL authors="vayner"

COPY build/libs/albion-rmt-backend-*.jar /opt/albion-rmt-backend.jar

RUN mkdir "/opt/logs"
RUN chmod +wrx /opt/logs

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/opt/albion-rmt-backend.jar"]