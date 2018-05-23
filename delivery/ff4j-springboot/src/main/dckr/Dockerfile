FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ff4j-springboot-1.0.0-SNAPSHOT-jar* app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]