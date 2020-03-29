FROM adoptopenjdk/openjdk13
RUN addgroup application && useradd -G application -M web-ui
USER web-ui:application
ARG JAR_FILE=target/application.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]