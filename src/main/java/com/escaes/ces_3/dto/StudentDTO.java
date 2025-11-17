package com.escaes.ces_3.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {


    private String nombre;

    private Integer edad;

    private String correo;

    private String telefono;

    private String ciudadResidencia;

    


    private List<AcademiclInforDto> informacionAcademica;

    private List<StudentPreferencesDTO> preferenciasEstudiante;



}
