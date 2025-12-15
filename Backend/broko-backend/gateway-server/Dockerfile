# Etapa de build
FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /app

COPY . /app

# Instala el parent primero
RUN mvn -f /app/pom.xml install

# Luego entra al servicio actual
WORKDIR /app/gateway-server

# Compila el microservicio
RUN mvn -B clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/gateway-server/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
