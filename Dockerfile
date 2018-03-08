FROM openjdk:8-jdk-alpine

MAINTAINER Johannes Molland <johannes.molland@difi.no>

ADD target/bolagsverket-client-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]