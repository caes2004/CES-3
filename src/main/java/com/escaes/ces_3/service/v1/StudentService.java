package com.escaes.ces_3.service.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.escaes.ces_3.dto.v1.StudentDTO;

/**
 * Servicio para la gestión de estudiantes.
 * 
 * Este servicio proporciona operaciones CRUD básicas para el manejo de
 * estudiantes.
 * Utiliza una lista en memoria para almacenar los datos. Spring Boot mapea
 * automáticamente
 * los objetos JSON recibidos en las peticiones HTTP a objetos DTO.
 * 
 * Nota: Este servicio utiliza almacenamiento en memoria. Los datos se perderán
 * al reiniciar
 * la aplicación. Para persistencia permanente, considere integrar una base de
 * datos.
 * 
 * @author Esteban Cano
 * @version 1.0
 */
@Service("Servicio de estudiantes en memoria")
public class StudentService {

    /**
     * Lista en memoria que almacena todos los estudiantes registrados.
     */
    private List<StudentDTO> studentDTOs = new ArrayList<>();

    /**
     * Guarda un nuevo estudiante en el sistema.
     * 
     * El estudiante debe tener un ID único asignado antes de llamar a este método.
     * Spring Boot mapea automáticamente el JSON del cuerpo de la petición HTTP al
     * objeto
     * StudentDTO.
     * 
     * @param request El objeto StudentDTO con los datos del estudiante a guardar.
     *                Debe contener un ID único previamente asignado.
     * @see StudentDTO
     */
    public void saveStudents(StudentDTO request) {
        studentDTOs.add(request);
    }

    /**
     * Obtiene la lista completa de todos los estudiantes registrados.
     * 
     * @return Una lista con todos los estudiantes almacenados en el sistema.
     *         Retorna una lista vacía si no hay estudiantes registrados.
     */
    public List<StudentDTO> getStudents() {
        return studentDTOs;
    }

    /**
     * Actualiza un estudiante existente mediante reemplazo completo (PUT
     * tradicional).
     * 
     * Este método implementa la semántica estándar de PUT: reemplaza completamente
     * el objeto existente con los datos proporcionados. Spring Boot mapea
     * automáticamente
     * el JSON del cuerpo de la petición al objeto StudentDTO.
     * 
     * Comportamiento:
     * - Busca el estudiante por su ID único
     * - Reemplaza completamente todos los campos del estudiante con los datos del
     * request
     * - Preserva el ID del estudiante (no permite cambiar el ID)
     * 
     * Ejemplo de uso:
     * PUT /student/put/{id}
     * {
     * "nombre": "Juan Pérez",
     * "edad": 25,
     * "correo": "juan@example.com"
     * }
     * 
     * @param id      El identificador único (UUID) del estudiante a actualizar
     * @param request El objeto StudentDTO con los nuevos datos del estudiante.
     *                Spring mapea automáticamente el JSON al DTO
     * @return El objeto StudentDTO actualizado con todos los nuevos valores
     * @throws RuntimeException Si no se encuentra un estudiante con el ID
     *                          proporcionado
     * @see StudentDTO
     */
    public StudentDTO updateStudent(UUID id, StudentDTO request) {
        StudentDTO estudiante = studentDTOs.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        request.setId(id);
        int index = studentDTOs.indexOf(estudiante);
        studentDTOs.set(index, request);

        return request;
    }

    /**
     * Actualiza parcialmente un estudiante existente (PATCH tradicional).
     * 
     * Este método implementa la semántica estándar de PATCH: actualiza solo los campos
     * que se proporcionan en el request, manteniendo los valores existentes para los
     * campos que no se envían o que son null.
     * 
     * Comportamiento:
     * - Busca el estudiante por su ID único
     * - Solo actualiza los campos que vienen en el request (que no son null)
     * - Mantiene los valores existentes para los campos no enviados
     * - Preserva el ID del estudiante (no permite cambiar el ID)
     * 
     * 
     * Ejemplo de uso:
     * PATCH /student/patch/{id}
     * {
     *   "nombre": "María González"
     * }
     * Solo actualizará el nombre, manteniendo edad, correo, etc. sin cambios.
     * 
     * @param id      El identificador único (UUID) del estudiante a actualizar
     * @param request El objeto StudentDTO con solo los campos que se desean actualizar.
     *                Los campos null o no enviados no se modificarán
     * @return El objeto StudentDTO actualizado con los cambios aplicados
     * @throws RuntimeException Si no se encuentra un estudiante con el ID proporcionado
     * @see StudentDTO
     */
    public StudentDTO patchStudent(UUID id, StudentDTO request) {
        StudentDTO estudiante = studentDTOs.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Actualización parcial: solo modificar campos que no son null
        if (request.getNombre() != null) {
            estudiante.setNombre(request.getNombre());
        }
        if (request.getEdad() != null) {
            estudiante.setEdad(request.getEdad());
        }
        if (request.getCorreo() != null) {
            estudiante.setCorreo(request.getCorreo());
        }
        if (request.getTelefono() != null) {
            estudiante.setTelefono(request.getTelefono());
        }
        if (request.getCiudadResidencia() != null) {
            estudiante.setCiudadResidencia(request.getCiudadResidencia());
        }
        if (request.getInformacionAcademica() != null) {
            estudiante.setInformacionAcademica(request.getInformacionAcademica());
        }
        if (request.getPreferenciasEstudiante() != null) {
            estudiante.setPreferenciasEstudiante(request.getPreferenciasEstudiante());
        }

        return estudiante;
    }

    /**
     * Elimina un estudiante existente mediante búsqueda por su identificador único
     * (Delete).
     *
     * Este método implementa la semántica estándar de DELETE: elimina completamente
     * el estudiante de la colección en memoria utilizando su UUID como criterio de
     * búsqueda.
     *
     * Comportamiento:
     * - Busca el estudiante por su ID único
     * - Si no existe, lanza una excepción indicando que el estudiante no fue
     * encontrado
     * - Si existe, lo elimina completamente de la lista de estudiantes
     *
     * Ejemplo de uso:
     * DELETE /student/delete/{id}
     *
     * @param id El identificador único (UUID) del estudiante que se desea eliminar
     *
     * @throws RuntimeException Si no se encuentra un estudiante con el ID
     *                          proporcionado
     *
     * @see StudentDTO
     */
    public void deleteStudent(UUID id) {

        boolean eliminado = studentDTOs.removeIf(s -> s.getId().equals(id));

        if (!eliminado) {
            throw new RuntimeException("Usuario con id: " + id + " no encontrado");
        }
    }

}
