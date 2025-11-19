# Etapa 1 — Construcción del JAR
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2 — Imagen final para correr el backend
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 9001

ENTRYPOINT ["java", "-jar", "app.jar"]
