package com.escaes.ces_3.service.v2;

import com.escaes.ces_3.dto.v2.StudentDTO;
import com.escaes.ces_3.mappers.StudentMapper;
import com.escaes.ces_3.models.Student;
import com.escaes.ces_3.models.StudentPreference;
import com.escaes.ces_3.repository.StudentJpaRepository;
import com.escaes.ces_3.repository.StudentPreferenceRepository;
import com.escaes.ces_3.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.stream.Collectors;

@Service("Servicio de estudiantes v2")
@RequiredArgsConstructor
public class StudentService implements CrudService<StudentDTO, UUID> {

    private final StudentJpaRepository studentJpaRepository;
    private final StudentPreferenceRepository studentPreferenceRepository;

    @Override
    @Transactional
    public StudentDTO save(StudentDTO studentDTO) {
        studentDTO.setId(UUID.randomUUID());
        studentJpaRepository.save(StudentMapper.toEntity(studentDTO));
        return studentDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDTO getById(UUID uuid) {
        return studentJpaRepository.findByIdWithAllRelations(uuid)
                .map(StudentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    @Transactional
    public void deleteById(UUID uuid) {
        studentJpaRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Student not found"));
        studentJpaRepository.deleteById(uuid);
    }

    @Transactional(readOnly = true)
    public List<StudentDTO> getAll() {
        // cargar estudiantes con academicInfos y materias
        List<Student> students = studentJpaRepository.findAllWithAcademicInfos();

        // collect student ids
        List<UUID> ids = students.stream().map(Student::getId).collect(Collectors.toList());

        // cargar preferencias de estudiantes
        List<StudentPreference> prefs = studentPreferenceRepository.findByStudentIds(ids);

        // grupos de preferencias por estudiante
        Map<UUID, List<StudentPreference>> prefsByStudent = prefs.stream()
                .collect(Collectors.groupingBy(p -> p.getStudent().getId()));

        // asignar preferencias a estudiantes
        students.forEach(s -> {
            List<StudentPreference> list = prefsByStudent.get(s.getId());
            if (list != null) {
                s.setPreferences(new HashSet<>(list));
            }
        });

        // map to DTOs
        return students.stream().map(StudentMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public StudentDTO update(UUID id, StudentDTO studentDTO) {

        studentJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        studentDTO.setId(id);

        studentJpaRepository.save(StudentMapper.toEntity(studentDTO));

        return studentDTO;
    }

}
