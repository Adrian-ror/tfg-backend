# Usa una imagen base con Maven y JDK
FROM maven:3.8.4-openjdk-17-slim AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de Maven y el código fuente
COPY pom.xml .
COPY src ./src

# Instala Maven si no está disponible
# RUN apt-get update && apt-get install -y maven

# Compila el proyecto con Maven
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR generado desde la fase de build
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto que usarás (ajústalo a tu necesidad)
EXPOSE 8090

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "app.jar"]