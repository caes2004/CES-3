# Usa una imagen oficial de OpenJDK como base
FROM eclipse-temurin:21-jre

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR construido por Maven
COPY target/*.jar app.jar

# Expone el puerto en el que corre la app
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"] 