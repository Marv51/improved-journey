FROM openjdk:8-jre-alpine3.7
MAINTAINER Magenta Swamp Blaster

ADD ./target/webshop.eureka-1.0-SNAPSHOT.jar /home
CMD ["java", "-Xms16m", "-Xmx64m", "-XX:MaxMetaspaceSize=64m", "-XX:CompressedClassSpaceSize=8m", "-Xss256k", "-Xmn8m", "-XX:InitialCodeCacheSize=4m", "-XX:ReservedCodeCacheSize=8m", "-XX:MaxDirectMemorySize=16m", "-jar", "/home/webshop.eureka-1.0-SNAPSHOT.jar"]

EXPOSE 8761
