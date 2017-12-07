FROM tomcat:8.0
MAINTAINER Team

ADD ./target/Eureka-Server-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/
ADD ./conf/tomcat-users.xml /usr/local/tomcat/conf/

EXPOSE 8761
EXPOSE 8080
