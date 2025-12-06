package com.escaes.ces_3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.escaes.ces_3.models.Student;

public interface StudentJpaRepository extends JpaRepository<Student, UUID> {

}
