FROM amazoncorretto:20.0.2-alpine

COPY target ./target

EXPOSE 8761

ENTRYPOINT ["java","-jar","target/DiscoveryService-0.0.1-SNAPSHOT.jar"]