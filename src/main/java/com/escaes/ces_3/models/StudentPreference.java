package com.escaes.ces_3.models;

import java.util.HashSet;
import java.util.Set;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "preferencias_estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "student", "actividades" })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StudentPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "modalidad_estudio", length = 255)
    private String modalidadEstudio;

    @Column(name = "notificaciones", columnDefinition = "TEXT")
    private String notificaciones;

    @OneToMany(mappedBy = "preference", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<PreferenceActividad> actividades = new HashSet<>();

}
