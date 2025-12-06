package com.escaes.ces_3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración para el versionamiento de la API mediante path segments.
 * 
 * Esta configuración permite que la API soporte múltiples versiones usando
 * el patrón de URL: /api/v{version}/...
 * 
 * CÓMO FUNCIONA:
 * 
 * 1. configureApiVersioning():
 *    - usePathSegment(1) indica que la versión se encuentra en el primer segmento
 *      del path después del prefijo base
 *    - Esto permite que Spring extraiga la versión de la URL automáticamente
 * 
 * 2. configurePathMatch():
 *    - addPathPrefix("/api/v{version}/", ...) agrega el prefijo /api/v{version}/
 *      a todas las rutas de los controladores
 *    - El {version} es un placeholder que Spring reemplaza con el valor del
 *      atributo version en @RequestMapping del controlador
 * 
 * ESTRUCTURA DE URLs RESULTANTE:
 * 
 * Con @RequestMapping(value = "/student", version = "1") en el controlador:
 * - URL base del controlador: /student
 * - Prefijo agregado: /api/v1/
 * - URL final: /api/v1/student/...
 * 
 * Ejemplos de URLs:
 * - POST /api/v1/student/create
 * - GET /api/v1/student/get
 * - PUT /api/v1/student/put/{id}
 * - PATCH /api/v1/student/patch/{id}
 * - DELETE /api/v1/student/delete/{id}
 * 
 * VERSIONAMIENTO:
 * 
 * Para crear nuevas versiones, simplemente crea nuevos controladores con:
 * - @RequestMapping(value = "/student", version = "2") -> /api/v2/student/...
 * - @RequestMapping(value = "/student", version = "3") -> /api/v3/student/...
 * 
 * Cada versión puede tener su propia implementación, permitiendo:
 * - V1: Almacenamiento en memoria (actual)
 * - V2: Base de datos (futuro)
 * - V3: Redis cache (futuro)
 * - V4: Producción optimizada (futuro)
 * 
 * VENTAJAS DE ESTE ENFOQUE:
 * 
 * - URLs claras y explícitas: la versión es visible en la URL
 * - Compatibilidad: múltiples versiones pueden coexistir
 * - Fácil migración: los clientes pueden migrar gradualmente
 * - Sin headers adicionales: la versión está en la URL misma
 * 
 * NOTA IMPORTANTE:
 * 
 * El atributo version en @RequestMapping debe ser un número (String):
 * - version = "1" -> /api/v1/...
 * - version = "2" -> /api/v2/...
 * 
 * No uses "v1" o "v2" en el atributo version, ya que la "v" se agrega
 * automáticamente en el prefijo del path.
 * 
 * @author Esteban Cano
 * @version 1.0
 */
@Configuration
public class ApiVersionConfig implements WebMvcConfigurer {

    /**
     * Configura cómo Spring Boot extrae la versión de la URL.
     * 
     * usePathSegment(1) indica que la versión está en el primer segmento
     * del path después del prefijo base. Por ejemplo:
     * - URL: /api/v1/student/create
     * - Prefijo: /api/v
     * - Segmento 1: "1" (la versión)
     * 
     * Spring Boot extraerá automáticamente "1" como la versión y la
     * comparará con el atributo version del @RequestMapping del controlador.
     * 
     * @param configurer Configurador de versionamiento de API
     */
    @Override
    public void configureApiVersioning(final ApiVersionConfigurer configurer) {
        configurer.usePathSegment(1);
    }

    /**
     * Configura el prefijo que se agrega a todas las rutas de los controladores.
     * 
     * addPathPrefix("/api/v{version}/", ...) agrega el prefijo /api/v{version}/
     * a todas las rutas. El placeholder {version} se reemplaza con el valor
     * del atributo version del @RequestMapping del controlador.
     * 
     * Ejemplo:
     * - Controlador: @RequestMapping(value = "/student", version = "1")
     * - Ruta del método: @PostMapping("/create")
     * - URL resultante: /api/v1/student/create
     * 
     * HandlerTypePredicate.forAnyHandlerType() aplica el prefijo a todos
     * los tipos de handlers (controladores REST, controladores MVC, etc.)
     * 
     * @param configurer Configurador de coincidencia de paths
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api/v{version}/", HandlerTypePredicate.forAnyHandlerType());
    }
}
