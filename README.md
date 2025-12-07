# CES-3 - API de Gestión de Estudiantes

##  Proyecto Académico

Este es un proyecto académico desarrollado para demostrar la evolución de una API REST desde una implementación básica en memoria hasta un despliegue completo en la nube con tecnologías modernas.

##  Objetivo del Proyecto

El objetivo principal es crear múltiples versiones de una API REST con Spring Boot 4.0, evolucionando progresivamente desde una implementación simple hasta un sistema completo con:

- **V1**: Almacenamiento en memoria (implementación actual)
- **V2**: Integración con base de datos (JPA/Hibernate)
- **V3**: Implementación de caché con Redis
- **V4**: Despliegue con Docker en AWS EC2

##  Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 4.0.0**
- **Maven** (gestión de dependencias)
- **Lombok** (reducción de boilerplate)
- **Spring Web** (REST API)
- **Versionamiento de API** (mediante headers HTTP)

##  Estructura del Proyecto

```
ces-3/
├── src/
│   ├── main/
│   │   ├── java/com/escaes/ces_3/
│   │   │   ├── controller/          # Controladores REST
│   │   │   ├── service/             # Lógica de negocio
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   └── Ces3Application.java # Clase principal
│   │   └── resources/
│   │       └── application.yaml      # Configuración
│   └── test/
│       └── java/com/escaes/ces_3/
│           └── student.http          # Peticiones HTTP de prueba
├── Dockerfile                        # Configuración Docker
├── pom.xml                           # Dependencias Maven
└── README.md                         # Este archivo
```

##  Versionamiento de la API

El proyecto utiliza versionamiento de API mediante headers HTTP. Para acceder a cada versión, debes incluir el header correspondiente:

- **X-API-Version: v1** (legacy - almacenamiento en memoria)
- **X-API-Version: v2** (actual - integración con base de datos MySQL)
- **X-API-Version: v3** (futura - Redis)
- **X-API-Version: v4** (futura - producción)

### Ejemplo de uso:

```http
GET http://localhost:8080/student/get
X-API-Version: v2
Content-Type: application/json
```

##  Versión Actual: V2 (Integración con Base de Datos - MySQL)

### Resumen del estado actual

-  Conexión garantizada a una base de datos MySQL (configuraciones y profiles preparados).
-  Todos los endpoints principales (`create`, `get`, `put`, `patch`, `delete`) están funcionando con la capa de persistencia.
-  Aun se deben ajustar las salidas JSON para asegurar una estructura consistente y homogénea entre versiones/entidades.
-  Se ha detectado comportamiento que sugiere que la inconsistencia en las respuestas (y en algunos casos la falta de persistencia de cambios sobre entidades relacionadas) probablemente se deba al mapeo (mappers) y/o a la lógica en el servicio que maneja actualizaciones parciales/relaciones.
-  Actualmente, la entidad `Student` funciona correctamente (creación, lectura, actualización y eliminación). Los problemas más probables están en el mapeo o en la lógica que actualiza las entidades relacionadas cuando se hace un `PUT`/`PATCH`.

### Recomendaciones técnicas (estado actual)
- Revisar los mappers que convierten entre entidades y DTOs para asegurar que:
  - Se gestionen correctamente las colecciones y entidades relacionadas (merge vs replace).
  - Se apliquen restricciones de null/empty para no sobrescribir datos relacionados por accidente.
- Revisar los métodos de servicio que realizan actualizaciones parciales para garantizar transacciones y flush/merge correctos.
- Añadir pruebas de integración que ejerciten actualizaciones sobre entidades relacionadas y verifiquen persistencia en la base de datos.

### Endpoints Disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/student/create` | Crear un nuevo estudiante |
| GET | `/student/get` | Obtener todos los estudiantes |
| PUT | `/student/put/{id}` | Actualizar estudiante (reemplazo completo) |
| PATCH | `/student/patch/{id}` | Actualizar estudiante (parcial) |
| DELETE | `/student/delete/{id}` | Eliminar un estudiante |

### Modelo de Datos

**StudentDTO:**
- `id` (UUID)
- `nombre` (String)
- `edad` (Integer)
- `correo` (String)
- `telefono` (String)
- `ciudadResidencia` (String)
- `informacionAcademica` (List<AcademiclInforDto>)
- `preferenciasEstudiante` (List<StudentPreferencesDTO>)

##  Cómo Ejecutar el Proyecto

### Requisitos Previos

- Java 21 o superior
- Maven 3.6+ (o usar el wrapper incluido: `mvnw`)

### Ejecución Local

1. Clonar el repositorio:
```bash
git clone <url-del-repositorio>
cd ces-3
```

2. Compilar el proyecto:
```bash
./mvnw clean install
```

3. Ejecutar la aplicación:
```bash
./mvnw spring-boot:run
```

4. La API estará disponible en: `http://localhost:8080`

### Probar la API

Puedes usar el archivo `src/test/java/com/escaes/ces_3/student.http` que incluye ejemplos de todas las peticiones HTTP con el versionamiento correcto.

##  Roadmap de Desarrollo

###  Fase 1: V1 - Almacenamiento en Memoria (COMPLETADO)
- [x] Estructura básica del proyecto
- [x] CRUD completo de estudiantes
- [x] Versionamiento de API
- [x] Documentación básica

###  Fase 2: V2 - Base de Datos (IMPLEMENTADO, CON SALVEDADES)
- [x] Integración con JPA/Hibernate
- [x] Configuración de base de datos (MySQL) y profiles
- [x] Migraciones con Flyway (inicialización básica incluida)
- [x] Repositorios Spring Data
- [ ] Validaciones de datos y ajuste fino de mappers/servicios (pendiente)

> Nota: V2 está funcional y la conexión a MySQL está garantizada; sin embargo se recomienda revisar y ajustar los mappers y la lógica de servicio para asegurar la consistencia de salida JSON y la persistencia correcta de entidades relacionadas.

###  Fase 3: V3 - Redis Cache (PLANEADO)
- [ ] Integración de Spring Data Redis
- [ ] Configuración de caché
- [ ] Estrategias de caché (TTL, invalidación)
- [ ] Optimización de consultas frecuentes

###  Fase 4: V4 - Despliegue en AWS EC2 (PLANEADO)
- [ ] Configuración de Docker Compose
- [ ] Imágenes Docker para aplicación y Redis
- [ ] Configuración de AWS EC2
- [ ] Scripts de despliegue automatizado
- [ ] Configuración de seguridad (Security Groups)
- [ ] Monitoreo y logging

##  Docker

El proyecto incluye un `Dockerfile` para containerización. Para construir y ejecutar:

```bash
# Construir la imagen
docker build -t ces-3-api .

# Ejecutar el contenedor
docker run -p 8080:8080 ces-3-api
```

##  Notas Importantes

### Limitaciones relevantes (actualizado)

-  La entidad `Student` funciona correctamente con persistencia en MySQL.
-  Puede existir falta de persistencia de cambios en entidades relacionadas cuando se realizan actualizaciones parciales; esto es probablemente causado por problemas en el mapper o en la lógica del servicio que no realiza merge/flush/transacciones apropiadas.
-  Las salidas JSON aún deben estandarizarse para garantizar consistencia entre endpoints y versiones.

### Próximas Mejoras

- Implementar base de datos para persistencia
- Agregar validaciones de entrada
- Implementar manejo de errores global
- Agregar tests unitarios y de integración
- Documentación con Swagger/OpenAPI
- Autenticación JWT
- Logging estructurado

##  Autor

**Esteban Cano**

Proyecto desarrollado con fines académicos para el curso CES-3.

##  Licencia

Este proyecto es de uso académico. Todos los derechos reservados.

##  Contribuciones

Este es un proyecto académico. Las contribuciones son bienvenidas para fines educativos.

##  Recursos Adicionales

- [Documentación Spring Boot 4.0](https://spring.io/projects/spring-boot)
- [Spring Data Redis](https://spring.io/projects/spring-data-redis)
- [Docker Documentation](https://docs.docker.com/)
- [AWS EC2 Documentation](https://docs.aws.amazon.com/ec2/)

---

**Versión Actual:** V2 - Integración con Base de Datos - MySQL  
**Última Actualización:** 2025
