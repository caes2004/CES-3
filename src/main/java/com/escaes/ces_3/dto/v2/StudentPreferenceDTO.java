package com.escaes.ces_3.dto.v2;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentPreferenceDTO {

    private Long id;

    private String modalidadEstudio;

    private String notificaciones;

    private List<PreferenceActividadDTO> actividades;

}

