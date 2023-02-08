FROM jenkins/jenkins:lts-alpine-jdk17
ARG APP_NAME="chat"
ARG APP_VERSION="0.0.1-SNAPSHOT"
ARG JAR_FILE="/build/libs/${APP_NAME}-${APP_VERSION}.jar"

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
