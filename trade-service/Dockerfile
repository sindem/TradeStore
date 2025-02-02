#FROM openjdk:17-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} trade-service.jar
#ENTRYPOINT java  -jar /trade-service.jar


FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY . .
ENTRYPOINT java -jar target/trade-service-0.0.1-SNAPSHOT.jar