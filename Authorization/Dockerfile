FROM amazoncorretto:20.0.2-alpine

COPY target ./target

EXPOSE 8086

ENTRYPOINT ["java","-jar","target/Authorization-0.0.1-SNAPSHOT.jar"]