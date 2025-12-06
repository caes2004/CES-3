package com.escaes.ces_3.dto.v1;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPreferencesDTO {

    private String modalidadEstudio;

    private List<String> actividadesExtraCurriculares = new ArrayList<>();

    private Map<String, Boolean> notificaciones = new HashMap<>();

}
