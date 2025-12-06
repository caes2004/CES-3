package com.escaes.ces_3.controller.v2;

import com.escaes.ces_3.dto.v2.StudentDTO;
import com.escaes.ces_3.service.v2.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para la gestión de estudiantes - Versión 2 (V2).
 *
 * Esta versión utiliza almacenamiento en base de datos.
 *
 * Para acceder a este controlador, el cliente debe incluir en la petición HTTP
 * uno de los siguientes headers:
 * - X-API-Version: v2
 * - Accept-Version: v2
 *
 * Ejemplo de petición:
 * GET /student/get
 * Headers:
 *   X-API-Version: v2
 *   Content-Type: application/json
 *
 * @version 2.0 - Almacenamiento en base de datos
 */

@RestController("studentControllerV2")
@RequestMapping(value = "/student", version = "2")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<StudentDTO> createStudentV2(@RequestBody StudentDTO request) {
        StudentDTO createdStudent = studentService.save(request);
        return ResponseEntity.status(201).body(createdStudent);
    }

    @GetMapping("/get")
    public ResponseEntity <List<StudentDTO>> getStudentV2() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<StudentDTO> updateStudentV2(@PathVariable UUID id, @RequestBody StudentDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.update(request));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<StudentDTO> patchStudentV2(@PathVariable UUID id, @RequestBody StudentDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.update(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudentV2(@PathVariable("id") UUID id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
