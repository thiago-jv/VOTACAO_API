FROM openjdk:11-jre

WORKDIR /app

ARG JAR_FILE

COPY target/${JAR_FILE} /app/api.jar

EXPOSE 8089

CMD ["java", "-jar", "api.jar"]
