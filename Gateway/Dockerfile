FROM amazoncorretto:20.0.2-alpine

COPY target ./target

EXPOSE 9000

ENTRYPOINT ["java","-jar","target/Gateway-0.0.1-SNAPSHOT.jar"]