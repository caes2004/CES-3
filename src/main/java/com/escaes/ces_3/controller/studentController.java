package com.escaes.ces_3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escaes.ces_3.dto.StudentDTO;
import com.escaes.ces_3.service.StudentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class studentController {

    private final StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<StudentDTO>crearEstudiante(@RequestBody StudentDTO request) {
        
        studentService.saveStudents(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @GetMapping("/get")
    public ResponseEntity<List<StudentDTO>> ObtenerEstudiantes() {

        return ResponseEntity.ok(studentService.getStudents());
    }
    
    

}
