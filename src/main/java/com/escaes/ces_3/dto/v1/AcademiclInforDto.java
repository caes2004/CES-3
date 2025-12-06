package com.escaes.ces_3.dto.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademiclInforDto {

    private String programa;

    private Integer semestreActual;

    private Double promedioAcumulado;

    private List<String> materiasInscirtas = new ArrayList<>();

    private Map<String, Object> detalleMaterias = new HashMap<>();

    

}
