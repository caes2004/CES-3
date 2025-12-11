// java
package com.escaes.ces_3.dto.v2;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private UUID id;

    private String nombre;

    private Integer edad;

    private String correo;

    private String telefono;

    private String ciudadResidencia;

    @Builder.Default
    private String hash = UUID.randomUUID().toString();

    private List<AcademicInfoDTO> academicInfos;

    private List<StudentPreferenceDTO> preferences;

}