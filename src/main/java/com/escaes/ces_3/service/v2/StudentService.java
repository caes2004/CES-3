package com.escaes.ces_3.service.v2;

import com.escaes.ces_3.dto.v2.StudentDTO;
import com.escaes.ces_3.mappers.StudentMapper;
import com.escaes.ces_3.repository.StudentJpaRepository;
import com.escaes.ces_3.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("Servicio de estudiantes v2")
@RequiredArgsConstructor
public class StudentService implements CrudService<StudentDTO, UUID> {

    private final StudentJpaRepository studentJpaRepository;

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        studentDTO.setId(UUID.randomUUID());
        studentJpaRepository.save(StudentMapper.toEntity(studentDTO));
        return studentDTO;
    }

    @Override
    public StudentDTO getById(UUID uuid) {
        return studentJpaRepository.findById(uuid)
                .map(StudentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public void deleteById(UUID uuid) {
        getById(uuid);
        studentJpaRepository.deleteById(uuid);
    }

    public List<StudentDTO> getAll() {
        return studentJpaRepository.findAll()
                .stream()
                .map(StudentMapper::toDto)
                .toList();
    }

    public StudentDTO update(StudentDTO studentDTO) {
        getById(studentDTO.getId());
        studentJpaRepository.save(StudentMapper.toEntity(studentDTO));
        return studentDTO;
    }
}
