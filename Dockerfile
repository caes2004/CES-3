# ---------- STAGE 1: build ----------
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copiamos solo lo necesario para cachear dependencias primero
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Descargamos dependencias
RUN mvn -B -f pom.xml dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Compila el jar (sin tests para acelerar)
RUN mvn -B clean package -DskipTests

# ---------- STAGE 2: runtime ----------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiamos el JAR construido desde la etapa de build
COPY --from=build /workspace/target/*.jar app.jar

# Si tu app usa otro puerto, cámbialo
EXPOSE 8080

# Opciones JVM recomendadas
ENV JAVA_OPTS="-Xms128m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
