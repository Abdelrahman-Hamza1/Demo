FROM amazoncorretto:20.0.2-alpine

COPY target ./target

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/Books-0.0.1-SNAPSHOT.jar"]