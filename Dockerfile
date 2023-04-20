FROM openjdk:11-jdk

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8181

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

#docker build -t student_management_back_end .