FROM amazoncorretto:20.0.2-alpine

COPY target ./target

EXPOSE 8081

ENTRYPOINT ["java","-jar","target/Auction-0.0.1-SNAPSHOT.jar"]