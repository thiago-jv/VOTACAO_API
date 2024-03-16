FROM maven:3.6.3-openjdk-11 as build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre
COPY --from=build /home/app/target/app.war /user/local/lib/app.war
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/user/local/lib/app.war"]

