FROM amazoncorretto:20.0.2-alpine

COPY target ./target

EXPOSE 8084

ENTRYPOINT ["java","-jar","target/AuthorService-0.0.1-SNAPSHOT.jar"]