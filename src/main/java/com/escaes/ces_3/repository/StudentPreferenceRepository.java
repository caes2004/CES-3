package com.escaes.ces_3.repository;

import com.escaes.ces_3.models.StudentPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface StudentPreferenceRepository extends JpaRepository<StudentPreference, Long> {

    @Query("""
        SELECT DISTINCT p FROM StudentPreference p
        LEFT JOIN FETCH p.actividades a
        WHERE p.student.id IN :ids
        """)
    List<StudentPreference> findByStudentIds(@Param("ids") List<UUID> ids);

}

