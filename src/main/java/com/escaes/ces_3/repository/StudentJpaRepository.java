package com.escaes.ces_3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;
import java.util.List;
import java.util.Optional;
import com.escaes.ces_3.models.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJpaRepository extends JpaRepository<Student, UUID> {

    @Query("""
        SELECT DISTINCT s FROM Student s
        LEFT JOIN FETCH s.academicInfos ai
        LEFT JOIN FETCH ai.materias
        LEFT JOIN FETCH s.preferences p
        LEFT JOIN FETCH p.actividades
        WHERE s.id = :id
        """)
    Optional<Student> findByIdWithAllRelations(@Param("id") UUID id);

    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.academicInfos ai LEFT JOIN FETCH ai.materias")
    List<Student> findAllWithAcademicInfos();
}
