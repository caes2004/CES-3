package com.escaes.ces_3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escaes.ces_3.dto.StudentDTO;
import com.escaes.ces_3.service.StudentService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;




/**
 * Controlador REST para la gestión de estudiantes - Versión 1 (V1).
 * 
 * Esta versión utiliza almacenamiento en memoria. Los datos se guardan en una lista
 * y se pierden al reiniciar la aplicación.
 * 
 * Para acceder a este controlador, el cliente debe incluir en la petición HTTP
 * uno de los siguientes headers:
 * - X-API-Version: v1
 * - Accept-Version: v1
 * 
 * Ejemplo de petición:
 * GET /student/get
 * Headers:
 *   X-API-Version: v1
 *   Content-Type: application/json
 * 
 * @version 1.0 - Almacenamiento en memoria
 */
@RestController
@RequestMapping(value = "/student", version = "v1")
@RequiredArgsConstructor
public class studentController {

    private final StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<StudentDTO>crearEstudiante(@RequestBody StudentDTO request) {

        request.setId(UUID.randomUUID());
        studentService.saveStudents(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @GetMapping("/get")
    public ResponseEntity<List<StudentDTO>> ObtenerEstudiantes() {

        return ResponseEntity.ok(studentService.getStudents());
    }
    
    @PutMapping("/put/{id}")
    public ResponseEntity<StudentDTO> actualizarEstudiantePUT(@PathVariable UUID id, @RequestBody StudentDTO request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }


    @PatchMapping("/patch/{id}")
    public ResponseEntity<StudentDTO> actualizarEstudiantePATCH(@PathVariable UUID id, @RequestBody StudentDTO request) {
        return ResponseEntity.ok(studentService.patchStudent(id, request));
    }

  
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

}
