FROM maven:3.8.5-openjdk-17 AS build
COPY ..
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/UserAuthenticationSystem-1-0.0.1-SNAPSHOT.jar UserAuthenticationSystem-1.jar
EXPOSE 2021
ENTRYPOINT ["java","-jar","UserAuthenticationSystem-1.jar"]