FROM openjdk:11-jre-slim

MAINTAINER Digitaliseringsdirektoratet <servicedesk@digdir.no>

ENV JAVA_OPTS="" \
    APP_DIR=/var/lib/digdir/ \
    APP_FILE_NAME=app.jar

RUN addgroup --system --gid 1001 java && adduser --system --uid 1001 --group java
RUN chown -R java:java /opt
RUN mkdir /logs && chown -R java:java /logs

ARG jarPath
ADD --chown=java:java $jarPath ${APP_DIR}$APP_FILE_NAME

RUN chmod -R +x $APP_DIR
USER java
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar ${APP_DIR}$APP_FILE_NAME ${0} ${@}"]

HEALTHCHECK --interval=30s --timeout=2s --retries=3 \
CMD wget -qO- "http://localhost:8081/manage/health" || exit 1