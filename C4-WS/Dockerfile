FROM openjdk:11.0.4-jre-slim-buster
COPY target/backend-0.0.1-SNAPSHOT.jar /usr/src/app.jar
WORKDIR /usr/src
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
