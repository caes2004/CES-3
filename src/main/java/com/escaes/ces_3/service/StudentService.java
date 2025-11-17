package com.escaes.ces_3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.escaes.ces_3.dto.StudentDTO;

@Service("Servicio de estudiantes")
public class StudentService {

    private List<StudentDTO>studentDTOs=new ArrayList<>();

    public void saveStudents(StudentDTO request){
        studentDTOs.add(request);
    }

    public List<StudentDTO> getStudents(){
        return studentDTOs;
    }

}
