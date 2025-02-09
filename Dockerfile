FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY target/lscAdmim-0.0.1-SNAPSHOT.jar.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
