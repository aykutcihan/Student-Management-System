# Java Uygulaması
FROM maven:3.8.3-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package

# PostgreSQL Veritabanı
FROM postgres:13.2

# Uygulama ve Veritabanı Docker konteynerlarını birleştirme
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/sm-app-1.jar .
COPY --from=postgres /docker-entrypoint-initdb.d /docker-entrypoint-initdb.d
CMD ["java", "-jar", "sm-app-1.jar"]
