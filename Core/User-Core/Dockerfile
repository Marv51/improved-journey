FROM openjdk:8-jre-alpine3.7
MAINTAINER Magenta Swamp Blaster

ADD ./target/core.user-1.0-SNAPSHOT.jar /home
CMD ["java", "-jar", "/home/core.user-1.0-SNAPSHOT.jar"]

EXPOSE 8080
