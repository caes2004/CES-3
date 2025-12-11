package com.escaes.ces_3.mappers;

import java.util.*;
import java.util.stream.Collectors;

import com.escaes.ces_3.dto.v2.AcademicInfoDTO;
import com.escaes.ces_3.dto.v2.AcademicMateriaDTO;
import com.escaes.ces_3.dto.v2.PreferenceActividadDTO;
import com.escaes.ces_3.dto.v2.StudentDTO;
import com.escaes.ces_3.dto.v2.StudentPreferenceDTO;
import com.escaes.ces_3.models.AcademicInfo;
import com.escaes.ces_3.models.AcademicMateria;
import com.escaes.ces_3.models.PreferenceActividad;
import com.escaes.ces_3.models.Student;
import com.escaes.ces_3.models.StudentPreference;

public class StudentMapper {

    // Conversión entidad -> DTO
    public static StudentDTO toDto(Student student) {
        if (student == null) return null;
        StudentDTO dto = StudentDTO.builder()
                .id(student.getId())
                .nombre(student.getNombre())
                .edad(student.getEdad())
                .correo(student.getCorreo())
                .telefono(student.getTelefono())
                .ciudadResidencia(student.getCiudadResidencia())
                .hash(UUID.randomUUID().toString())
                .build();

        if (student.getAcademicInfos() != null && !student.getAcademicInfos().isEmpty()) {
            dto.setAcademicInfos(student.getAcademicInfos().stream()
                    .filter(Objects::nonNull)
                    .map(StudentMapper::academicInfoToDto)
                    .collect(Collectors.toList()));
        }

        if (student.getPreferences() != null && !student.getPreferences().isEmpty()) {
            dto.setPreferences(student.getPreferences().stream()
                    .filter(Objects::nonNull)
                    .map(StudentMapper::preferenceToDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    // Conversión DTO -> entidad
    public static Student toEntity(StudentDTO dto) {
        if (dto == null) return null;
        Student student = new Student();
        student.setId(dto.getId());
        student.setNombre(dto.getNombre());
        student.setEdad(dto.getEdad());
        student.setCorreo(dto.getCorreo());
        student.setTelefono(dto.getTelefono());
        student.setCiudadResidencia(dto.getCiudadResidencia());

        if (dto.getAcademicInfos() != null) {
            Set<AcademicInfo> infos = dto.getAcademicInfos().stream()
                    .filter(Objects::nonNull)
                    .map(aDto -> academicInfoToEntity(aDto, student))
                    .collect(Collectors.toSet());
            student.setAcademicInfos(infos);
        } else {
            student.setAcademicInfos(new HashSet<>());
        }

        if (dto.getPreferences() != null) {
            Set<StudentPreference> prefs = dto.getPreferences().stream()
                    .filter(Objects::nonNull)
                    .map(pDto -> preferenceToEntity(pDto, student))
                    .collect(Collectors.toSet());
            student.setPreferences(prefs);
        } else {
            student.setPreferences(new HashSet<>());
        }

        return student;
    }

    // ---- AcademicInfo <-> DTO ----
    private static AcademicInfoDTO academicInfoToDto(AcademicInfo ai) {
        if (ai == null) return null;
        AcademicInfoDTO dto = AcademicInfoDTO.builder()
                .id(ai.getId())
                .programa(ai.getPrograma())
                .semestreActual(ai.getSemestreActual())
                .promedioAcumulado(ai.getPromedioAcumulado())
                .detalleMaterias(ai.getDetalleMaterias())
                .build();

        if (ai.getMaterias() != null) {
            dto.setMaterias(ai.getMaterias().stream()
                    .filter(Objects::nonNull)
                    .map(StudentMapper::materiaToDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private static AcademicInfo academicInfoToEntity(AcademicInfoDTO dto, Student parent) {
        if (dto == null) return null;
        AcademicInfo ai = new AcademicInfo();
        ai.setId(dto.getId());
        ai.setPrograma(dto.getPrograma());
        ai.setSemestreActual(dto.getSemestreActual());
        ai.setPromedioAcumulado(dto.getPromedioAcumulado());
        ai.setDetalleMaterias(dto.getDetalleMaterias());
        ai.setStudent(parent);

        if (dto.getMaterias() != null) {
            Set<AcademicMateria> materias = dto.getMaterias().stream()
                    .filter(Objects::nonNull)
                    .map(mDto -> materiaToEntity(mDto, ai))
                    .collect(Collectors.toSet());
            ai.setMaterias(materias);
        } else {
            ai.setMaterias(new HashSet<>());
        }

        return ai;
    }

    // ---- AcademicMateria <-> DTO ----
    private static AcademicMateriaDTO materiaToDto(AcademicMateria m) {
        if (m == null) return null;
        return AcademicMateriaDTO.builder()
                .id(m.getId())
                .materia(m.getMateria())
                .build();
    }

    private static AcademicMateria materiaToEntity(AcademicMateriaDTO dto, AcademicInfo parent) {
        if (dto == null) return null;
        AcademicMateria m = new AcademicMateria();
        m.setId(dto.getId());
        m.setMateria(dto.getMateria());
        m.setAcademicInfo(parent);
        return m;
    }

    // ---- StudentPreference <-> DTO ----
    private static StudentPreferenceDTO preferenceToDto(StudentPreference p) {
        if (p == null) return null;
        StudentPreferenceDTO dto = StudentPreferenceDTO.builder()
                .id(p.getId())
                .modalidadEstudio(p.getModalidadEstudio())
                .notificaciones(p.getNotificaciones())
                .build();

        if (p.getActividades() != null) {
            dto.setActividades(p.getActividades().stream()
                    .filter(Objects::nonNull)
                    .map(StudentMapper::actividadToDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private static StudentPreference preferenceToEntity(StudentPreferenceDTO dto, Student parent) {
        if (dto == null) return null;
        StudentPreference p = new StudentPreference();
        p.setId(dto.getId());
        p.setModalidadEstudio(dto.getModalidadEstudio());
        p.setNotificaciones(dto.getNotificaciones());
        p.setStudent(parent);

        if (dto.getActividades() != null) {
            Set<PreferenceActividad> actividades = dto.getActividades().stream()
                    .filter(Objects::nonNull)
                    .map(aDto -> actividadToEntity(aDto, p))
                    .collect(Collectors.toSet());
            p.setActividades(actividades);
        } else {
            p.setActividades(new HashSet<>());
        }

        return p;
    }

    // ---- PreferenceActividad <-> DTO ----
    private static PreferenceActividadDTO actividadToDto(PreferenceActividad a) {
        if (a == null) return null;
        return PreferenceActividadDTO.builder()
                .id(a.getId())
                .actividad(a.getActividad())
                .build();
    }

    private static PreferenceActividad actividadToEntity(PreferenceActividadDTO dto, StudentPreference parent) {
        if (dto == null) return null;
        PreferenceActividad a = new PreferenceActividad();
        a.setId(dto.getId());
        a.setActividad(dto.getActividad());
        a.setPreference(parent);
        return a;
    }

}
