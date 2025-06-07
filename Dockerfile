# ---------- Etapa 1: Build del proyecto con Maven ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Crear y moverse al directorio de trabajo
WORKDIR /app

# Copiar los archivos de configuración de Maven y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código fuente
COPY src ./src

# Compilar el proyecto y generar el JAR (sin ejecutar tests)
RUN mvn clean package -DskipTests

# ---------- Etapa 2: Imagen final para ejecución ----------
FROM eclipse-temurin:21-jdk-alpine

# Directorio donde se ejecutará la app
WORKDIR /app

# Copiar el JAR generado desde la etapa anterior
COPY --from=build /app/target/Gestion-Farmacia-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto que usa Spring Boot
EXPOSE 9520

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]