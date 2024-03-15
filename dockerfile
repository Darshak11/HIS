FROM openjdk:17
COPY ./target/his-0.0.1-SNAPSHOT.jar ./
WORKDIR ./
CMD ["java","-jar","his-0.0.1-SNAPSHOT.jar"]