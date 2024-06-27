FROM openjdk:21

COPY ./build/libs/application.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=test","/app.jar"]

EXPOSE 8080
