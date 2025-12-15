# Etapa de build
FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia todo el backend con su parent incluido
COPY . /app

# Instala el parent primero
RUN mvn -f /app/pom.xml install

# Luego entra al servicio actual
WORKDIR /app/eureka-server

# Compila el microservicio
RUN mvn -B clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/eureka-server/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app/app.jar"]
