FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/linkshrink-1.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]