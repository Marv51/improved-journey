FROM java:8-jre
MAINTAINER Magenta Swamp Blaster

ADD ./target/hystrix-0.0.1-SNAPSHOT.jar /home/
CMD ["java", "-jar", "/home/hystrix-0.0.1-SNAPSHOT.jar"]

EXPOSE 8081
