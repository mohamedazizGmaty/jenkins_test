FROM openjdk:17-jdk-slim
WORKDIR /app

COPY target/student-management-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8089

ENV DB_HOST=localhost
ENV DB_PORT=3306
ENV DB_NAME=studentdb
ENV DB_USER=root
ENV DB_PASSWORD=

ENTRYPOINT ["java", "-jar", "app.jar"]