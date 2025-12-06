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
public class AcademicInfoDTO {

    private Long id;

    private String programa;

    private Integer semestreActual;

    private Double promedioAcumulado;

    private String detalleMaterias;

    private List<AcademicMateriaDTO> materias;

}

