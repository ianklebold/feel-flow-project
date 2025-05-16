# Etapa 1: Compilación con Maven
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

# Crear carpeta de trabajo en la imagen
WORKDIR /app

# Copiar el contenido del proyecto
COPY . .

# Construir el proyecto sin correr los tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final para ejecución
FROM eclipse-temurin:21-alpine

# Crear carpeta de trabajo
WORKDIR /app

# Copiar el JAR generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Activar el perfil prod (esto es clave para Azure)
ENV SPRING_PROFILES_ACTIVE=prod

# Exponer el puerto en el que corre tu API
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]