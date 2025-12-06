package com.escaes.ces_3.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "academic_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "student", "materias" })
public class AcademicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(length = 255)
    private String programa;

    @Column(name = "semestre_actual")
    private Integer semestreActual;

    @Column(name = "promedio_acumulado")
    private Double promedioAcumulado;

    @Column(name = "detalle_materias", columnDefinition = "TEXT")
    private String detalleMaterias;

    @OneToMany(mappedBy = "academicInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AcademicMateria> materias;

}
