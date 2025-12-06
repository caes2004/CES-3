package com.escaes.ces_3.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "academicInfos", "preferences" })
public class Student {

    @Id
    @Column(length = 36)
    private UUID id;

    @Column(length = 255)
    private String nombre;

    @Column(columnDefinition = "INT")
    private Integer edad;

    @Column(length = 255)
    private String correo;

    @Column(length = 50)
    private String telefono;

    @Column(name = "ciudad_residencia", length = 255)
    private String ciudadResidencia;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AcademicInfo> academicInfos;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StudentPreference> preferences;

}
