FROM bellsoft/liberica-openjdk-alpine:17
# FROM cepgbaseacr.azurecr.io/docker.io/openjdk:17-slim

VOLUME /tmp

ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE={SPRING_PROFILES_ACTIVE}

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
