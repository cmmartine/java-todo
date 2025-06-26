FROM eclipse-temurin:21-jre

WORKDIR /app

ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]