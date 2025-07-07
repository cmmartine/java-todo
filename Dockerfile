FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get -y upgrade

WORKDIR /todo-app

ENTRYPOINT [ "./mvnw", "spring-boot:run" ]