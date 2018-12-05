FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD kkcms-gate-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Xms512m","-Dspring.profiles.active=prod","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]