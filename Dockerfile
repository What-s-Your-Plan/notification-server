FROM amazoncorretto:17-alpine

LABEL MAINTAINER="Jeonguk Kim <workju1124@gmail.com>"

COPY ./build/libs/*.jar wypl.jar

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=${PROFILE}", "wypl.jar"]