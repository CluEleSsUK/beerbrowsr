FROM openjdk:11-jdk-slim as BUILD
COPY . /src
WORKDIR /src
RUN ./gradlew jar --no-daemon

FROM openjdk:11-jdk-slim

COPY --from=BUILD /src/build/libs/api-all.jar /app/api.jar
WORKDIR /app

EXPOSE 8080
CMD ["java","-jar","api.jar"]