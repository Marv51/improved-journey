FROM openjdk:8-jre-alpine3.7
MAINTAINER Magenta Swamp Blaster

ADD ./target/core.user-1.0-SNAPSHOT.jar /home
CMD ["java", "-Xms16m", "-Xmx128m", "-XX:MaxMetaspaceSize=128m", "-XX:CompressedClassSpaceSize=16m", "-Xss256k", "-Xmn8m", "-XX:InitialCodeCacheSize=4m", "-XX:ReservedCodeCacheSize=12m", "-XX:MaxDirectMemorySize=16m", "-jar", "/home/core.user-1.0-SNAPSHOT.jar"]

EXPOSE 8080
